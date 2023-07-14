package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.*;

public class BookCoverImageUrlApiService extends BaseLibraryApiService {
    public BookCoverImageUrlApiService(Requester requester) {
        super(requester);
    }

    public List<String> getBookCoverImageUrl(String isbn) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookCoverImageUrlRequest(isbn);
        HttpResponse response = requester.get(request);
        checkResponse(response);

        // 因为只查了一本书，所以path(isbn)直接一步到位就行了
        JsonNode imageResultJson = objectMapper.readTree(response.getBody()).path("data").path(isbn);
        List<String> covers = new ArrayList<>(imageResultJson.size());
        for (JsonNode resultItemJson : imageResultJson) {
            covers.add(resultItemJson.path("imageUrl").asText()
                    .replaceAll("^//", "http://")
            );
        }

        return covers;
    }

    public Map<String, List<String>> getBookCoverImageUrl(List<String> isbnList) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookCoverImageUrlRequest(isbnList);
        HttpResponse response = requester.get(request);
        checkResponse(response);

        JsonNode data = objectMapper.readTree(response.getBody()).path("data");
        Map<String, List<String>> result = new HashMap<>(data.size());

        // 遍历data下每本书
        Iterator<Map.Entry<String, JsonNode>> it = data.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> bookItemJson = it.next();
            List<String> covers = new ArrayList<>(bookItemJson.getValue().size());
            for (JsonNode resultItemJson : bookItemJson.getValue()) {
                covers.add(resultItemJson.path("imageUrl").asText()
                        .replaceAll("^//", "http://")
                );
            }

            result.put(bookItemJson.getKey(), covers);
        }

        return result;
    }
}
