package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UndergradStudentInfoApiService extends UndergradApiServiceBase {

    public UndergradStudentInfoApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        return this.getPage(cookie, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, RequestClientOption requestOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.studentInfoRequest(cookies);
        HttpResponse response = requester.get(request, requestOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }

    public String getPage(String cookies) throws IOException, ApiException {
        return this.getPage(cookies, (RequestClientOption) null);
    }
}
