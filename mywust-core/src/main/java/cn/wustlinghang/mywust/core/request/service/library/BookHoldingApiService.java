package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.parsed.BookHolding;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.List;

public class BookHoldingApiService extends BaseLibraryApiService {


    public BookHoldingApiService(Requester requester) {
        super(requester);
    }

    public List<BookHolding> getHoldingList(String id)
            throws ApiException, IOException, ParseException {

        HttpRequest loanRequest = LibraryRequestFactory.bookHoldingRequest(id);
        HttpResponse loanResponse = requester.get(loanRequest);
        checkResponse(loanResponse);
        try {
            JsonNode data = objectMapper.readTree(loanResponse.getBody()).path("data");
            // 这里的holding是个string值，需要独立解析（坑人）
            JsonNode holdings = data.path("holdings");

            return objectMapper.readValue(holdings.asText("[]"), bookHoldingListType);
        } catch (JacksonException e) {
            throw new ParseException(e.getMessage(), "");
        }
    }
}
