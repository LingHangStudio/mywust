package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.data.library.origin.BaseLoanBook;
import cn.wustlinghang.mywust.data.library.origin.OriginBookSearchResult;
import cn.wustlinghang.mywust.data.library.origin.CurrentLoanBook;
import cn.wustlinghang.mywust.data.library.origin.HistoryLoanBook;
import cn.wustlinghang.mywust.data.library.parsed.BookHolding;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StringDeserializer;
import com.fasterxml.jackson.databind.module.SimpleModule;

import java.io.IOException;
import java.util.List;

public abstract class BaseLibraryApiService {
    protected static final ObjectMapper objectMapper = new ObjectMapper();

    protected static final JavaType loanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, BaseLoanBook.class);

    protected static final JavaType historyLoanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, HistoryLoanBook.class);

    protected static final JavaType currentLoanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, CurrentLoanBook.class);

    protected static final JavaType originBookSearchResultListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, OriginBookSearchResult.class);

    protected static final JavaType bookHoldingListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, BookHolding.class);

    protected final Requester requester;

    public BaseLibraryApiService(Requester requester) {
        this.requester = requester;
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        if (response.getStatusCode() != 200) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }
}