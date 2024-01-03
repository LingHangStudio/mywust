package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.PagingResult;
import cn.wustlinghang.mywust.data.library.origin.BookSearchRequest;
import cn.wustlinghang.mywust.data.library.origin.OriginBookSearchResult;
import cn.wustlinghang.mywust.data.library.parsed.BookHolding;
import cn.wustlinghang.mywust.data.library.parsed.BookSearchResult;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.util.StringUtil;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.commons.codec.binary.StringUtils;

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
            List<OriginBookSearchResult> originSearchResultList = objectMapper.treeToValue(dataList, originBookSearchResultListType);
            List<BookSearchResult> books = new ArrayList<>(dataList.size());
            for (OriginBookSearchResult result : originSearchResultList) {
                BookSearchResult book = this.convert(result);

                // 反序列化holding字段，原字段为字符串值，无法直接原样转换
                if (result.getHoldings() != null) {
                    List<BookHolding> holdings = objectMapper.readValue(result.getHoldings(), bookHoldingListType);
                    book.setHoldings(holdings);
                } else {
                    book.setHoldings(new ArrayList<>());
                }

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

    private BookSearchResult convert(OriginBookSearchResult result) {
        BookSearchResult bookSearchResult = new BookSearchResult();
        bookSearchResult.setBibId(result.getBibId());
        bookSearchResult.setHoldingTypes(result.getHoldingTypes());
        bookSearchResult.setAuthor(result.getAuthor());
        bookSearchResult.setCallNumber(result.getCallNumber());
        bookSearchResult.setDocType(result.getDocType());
        bookSearchResult.setGroupId(result.getGroupId());
        bookSearchResult.setIsbn(result.getIsbn());
        bookSearchResult.setBibNo(result.getBibNo());
        bookSearchResult.setTitle(result.getTitle());
        bookSearchResult.setItemCount(result.getItemCount());
        bookSearchResult.setCircCount(result.getCircCount());
        bookSearchResult.setPubYear(result.getPubYear());
        bookSearchResult.setClassNumber(result.getClassNumber());
        bookSearchResult.setPublisher(result.getPublisher());
//        bookSearchResult.setHoldings(result.getHoldings());

        return bookSearchResult;
    }
}
