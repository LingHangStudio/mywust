package cn.linghang.mywust.core.request.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Map;

/**
 * 缓考申请查询
 */
public class UndergradExamDelayApiService extends UndergradApiServiceBase {
    public UndergradExamDelayApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        if (params == null) {
            throw new ApiException(ApiException.Code.INTERNAL_EXCEPTION);
        }

        String term = params.get("term");
        String activityId = params.get("activityId");
        if (term == null || activityId == null) {
            throw new ApiException(ApiException.Code.INTERNAL_EXCEPTION);
        }

        return this.getPage(term, activityId, cookie, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String term, String activityId, String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxRequestFactory.examDelayApplicationListRequest(term, activityId, cookie);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String term, String activityId, String cookie) throws ApiException, IOException {
        return this.getPage(term, activityId, cookie, null);
    }

    public ExamActivity[] getActivities(String term, String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxRequestFactory.examActivityListRequest(term, cookie);
        HttpResponse response = requester.get(request, option);
        this.checkResponse(response);

        try {
            return new ObjectMapper().readValue(response.getBody(), ExamActivity[].class);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, "解析考试活动id列表失败：响应数据：" + response.getStringBody());
        }
    }

    public ExamActivity[] getActivities(String term, String cookie) throws ApiException, IOException {
        return this.getActivities(term, cookie, null);
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ExamActivity {
        @JsonProperty("cj0701id")
        public String id;

        @JsonProperty("cjlrmc")
        public String name;
    }
}
