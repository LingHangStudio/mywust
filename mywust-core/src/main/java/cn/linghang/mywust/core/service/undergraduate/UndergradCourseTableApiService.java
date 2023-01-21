package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UndergradCourseTableApiService extends UndergradApiServiceBase {

    private static final String[] NECESSARY_PARAMS = {"term"};

    public UndergradCourseTableApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        for (String key : NECESSARY_PARAMS) {
            if (params.get(key) == null) {
                throw new ApiException(ApiException.Code.PARAM_WRONG_EXCEPTION);
            }
        }

        return this.getPage(params.get(NECESSARY_PARAMS[0]), cookie, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String term, String cookies, RequestClientOption requestClientOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.courseTablePageRequest(term, cookies);
        HttpResponse response = requester.post(request, requestClientOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }

    public String getPage(String term, String cookies) throws IOException, ApiException {
        return getPage(term, cookies, null);
    }
}
