package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.parsed.BookDetail;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.util.StringUtil;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class BookDetailApiService extends BaseLibraryApiService {
    public BookDetailApiService(Requester requester) {
        super(requester);
    }

    public BookDetail getBookDetail(String bookId) throws ApiException, IOException {
        HttpRequest infoRequest = LibraryRequestFactory.bookInfoRequest(bookId);
        HttpResponse infoResponse = requester.get(infoRequest);
        checkResponse(infoResponse);

        JsonNode data = objectMapper.readTree(infoResponse.getBody())
                .path("data").path("map");

        JsonNode baseInfo = data.path("baseInfo").path("map");
        JsonNode detailInfo = data.path("detailInfo").path("map");

        Map<String, String> extraInfoMap = new HashMap<>(detailInfo.size());
        Iterator<Map.Entry<String, JsonNode>> it = detailInfo.fields();
        while (it.hasNext()) {
            Map.Entry<String, JsonNode> field = it.next();
            extraInfoMap.put(field.getKey(), StringUtil.cleanHtml(field.getValue().asText("无")));
        }

        String isbn = baseInfo.get("isbn").asText("无");
        String author = baseInfo.path("author").asText("无");
        String title = baseInfo.path("title").asText("无");

        HttpRequest doubanInfoRequest = LibraryRequestFactory.bookDoubanInfoRequest(isbn);
        HttpResponse doubanInfoResponse = requester.get(doubanInfoRequest);
        checkResponse(doubanInfoResponse);

        JsonNode doubanInfo = objectMapper.readTree(doubanInfoResponse.getBody()).path("data");
        String authorDescribe = doubanInfo.path("authorInfo").asText("无");
        String catalog = doubanInfo.path("catalog").asText("无");
        String summary = doubanInfo.path("content").asText("无");
        String introduction = doubanInfo.path("intro").asText(summary);
        String coverUrl = doubanInfo.path("imageUrl").asText("")
                .replaceAll("^//", "http://");

        return BookDetail.builder()
                .isbn(isbn)
                .author(author)
                .title(title)
                .extraInfoMap(extraInfoMap)

                .authorDescribe(StringUtil.cleanHtml(authorDescribe))
                .catalog(StringUtil.cleanHtml(catalog))
                .summary(StringUtil.cleanHtml(summary))
                .introduction(StringUtil.cleanHtml(introduction))
                .coverUrl(coverUrl)

                .build();
    }
}
