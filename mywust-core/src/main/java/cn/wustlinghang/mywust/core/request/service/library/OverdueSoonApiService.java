package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.data.library.PagingResult;
import cn.wustlinghang.mywust.data.library.origin.BaseLoanBook;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.List;

@Slf4j
public class OverdueSoonApiService extends BaseLibraryApiService {
    public OverdueSoonApiService(Requester requester) {
        super(requester);
    }

    public PagingResult<List<BaseLoanBook>> getOverdueSoon(String cookie, int page, int pageSize)
            throws ApiException, IOException, ParseException {

        HttpRequest request = LibraryRequestFactory.overdueSoonRequest(cookie, page, pageSize);
        HttpResponse response = requester.get(request);
        checkResponse(response);
        try {
            // todo 这里即将到期书籍字段跟当前借阅和借阅历史字段格式貌似有点不太一样（data字段是个数组）
            //      但是目前手上没有样本，因此这里还需要再测试测试
            JsonNode data = objectMapper.readTree(response.getBody()).path("data");

            // 获取，计算分页数据
            pageSize = data.path("pageSize").asInt(pageSize);
            int currentPage = data.path("currentPage").asInt(page);
            int totalResult = data.path("total").asInt(0);
            int totalPage = data.path("totalPage").asInt(0);

            JsonNode itemsJson = data.path("items");
            List<BaseLoanBook> baseLoanBooks = objectMapper.treeToValue(itemsJson, loanBookListType);

            return PagingResult.<List<BaseLoanBook>>builder()
                    .currentPage(currentPage)
                    .totalResult(totalResult)
                    .pageSize(pageSize)
                    .totalPage(totalPage)
                    .data(baseLoanBooks)
                    .build();

        } catch (JacksonException e) {
            throw new ParseException(e.getMessage(), "");
        }
    }
}
