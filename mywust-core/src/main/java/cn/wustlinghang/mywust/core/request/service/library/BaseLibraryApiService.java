package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.data.library.origin.BaseLoanBook;
import cn.wustlinghang.mywust.data.library.origin.BookSearchResult;
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

    static {
        objectMapper.configure(DeserializationFeature.FAIL_ON_INVALID_SUBTYPE, false);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        SimpleModule module = new SimpleModule();
        module.addDeserializer(String.class, new SpecialStringDeserializer());
        objectMapper.registerModule(module);
    }

    protected static final JavaType loanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, BaseLoanBook.class);

    protected static final JavaType historyLoanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, HistoryLoanBook.class);

    protected static final JavaType currentLoanBookListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, CurrentLoanBook.class);

    protected static final JavaType bookSearchResultListType =
            objectMapper.getTypeFactory().constructParametricType(List.class, BookSearchResult.class);

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

/**
 * <p>继承自原版StringDeserializer，特殊定制的字符串反序列化器</p>
 * <p>测试时偶然发现的图书馆搜索api返回的json中`pub_year`和`abstract`等一些本应该是字符串值的字段有概率会出现为空数组的奇葩情况...</p>
 * <p>想了半天也没想明白那系统究竟是怎么在字符串里蹦出`[]`的...</p>
 */
class SpecialStringDeserializer extends StringDeserializer {
    @Override
    public String deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        // The critical path: ensure we handle the common case first.
        if (p.hasToken(JsonToken.VALUE_STRING)) {
            return p.getText();
        }
        // [databind#381]
        if (p.hasToken(JsonToken.START_ARRAY)) {
            // 仅修改了这行
            return p.getValueAsString("");
        }
        return _parseString(p, ctxt, this);
    }
}