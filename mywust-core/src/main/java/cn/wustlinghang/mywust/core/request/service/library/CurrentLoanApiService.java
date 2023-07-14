package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.PagingResult;
import cn.wustlinghang.mywust.data.library.origin.CurrentLoanBook;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public class CurrentLoanApiService extends BaseLibraryApiService {
    public CurrentLoanApiService(Requester requester) {
        super(requester);
    }

    public PagingResult<List<CurrentLoanBook>> getCurrentLoan(String cookie, int page, int pageSize)
            throws ApiException, IOException, ParseException {

        HttpRequest loanRequest = LibraryRequestFactory.currentLoanRequest(cookie, page, pageSize);
        HttpResponse loanResponse = requester.get(loanRequest);
        checkResponse(loanResponse);
        try {
            JsonNode data = objectMapper.readTree(loanResponse.getBody()).path("data");

            // 获取，计算分页数据
            pageSize = data.path("pageSize").asInt(pageSize);
            int currentPage = data.path("currentPage").asInt(page);
            int totalResult = data.path("total").asInt(0);
            int totalPage = data.path("totalPage").asInt(0);

            JsonNode itemsJson = data.path("items");
            List<CurrentLoanBook> books = objectMapper.treeToValue(itemsJson, currentLoanBookListType);

            return PagingResult.<List<CurrentLoanBook>>builder()
                    .currentPage(currentPage)
                    .totalResult(totalResult)
                    .pageSize(pageSize)
                    .totalPage(totalPage)
                    .data(books)
                    .build();

        } catch (JacksonException e) {
            throw new ParseException(e.getMessage(), "");
        }
    }
}
