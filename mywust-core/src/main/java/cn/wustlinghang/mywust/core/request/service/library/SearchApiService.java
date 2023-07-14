package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.PagingResult;
import cn.wustlinghang.mywust.data.library.origin.BookSearchRequest;
import cn.wustlinghang.mywust.data.library.origin.BookSearchResult;
import cn.wustlinghang.mywust.data.library.parsed.BookHolding;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SearchApiService extends BaseLibraryApiService {
    public SearchApiService(Requester requester) {
        super(requester);
    }

    public PagingResult<List<BookSearchResult>> search(String keyword, int page, int pageSize)
            throws ApiException, IOException, ParseException {
        return search(new BookSearchRequest(keyword, pageSize, page));
    }

    public PagingResult<List<BookSearchResult>> search(BookSearchRequest request)
            throws ApiException, IOException, ParseException {
        HttpRequest searchRequest = LibraryRequestFactory.bookSearchRequest(request);
        HttpResponse searchResponse = requester.post(searchRequest);
        checkResponse(searchResponse);

        try {
            JsonNode data = objectMapper.readTree(searchResponse.getBody()).path("data");

            int pageSize = data.path("limit").asInt(request.getPageSize());
            int currentPage = data.path("offset").asInt(request.getPage()) / pageSize + 1;
            int totalResult = data.path("actualTotal").asInt(0);
            int totalPage = totalResult / pageSize + 1;

            JsonNode dataList = data.get("dataList");
            List<BookSearchResult> books = new ArrayList<>(dataList.size());
            for (JsonNode item : dataList) {
                // 反序列化holding字段，原字段为字符串值，无法直接原样转换
                List<BookHolding> holdings = objectMapper.readValue(
                        item.path("holdings").asText("[]"), bookHoldingListType);
                BookSearchResult book = objectMapper.treeToValue(item, BookSearchResult.class);
                book.setHoldings(holdings);
                books.add(book);
            }

            return PagingResult.<List<BookSearchResult>>builder()
                    .currentPage(currentPage)
                    .totalResult(totalResult)
                    .pageSize(pageSize)
                    .totalPage(totalPage)
                    .data(books)
                    .build();

        } catch (JacksonException e) {
            e.printStackTrace();
            throw new ParseException(e.getMessage(), "");
        }
    }
}
