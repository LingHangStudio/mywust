package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class StudentInfoApiService extends UndergraduateApiService {

    public StudentInfoApiService(Requester requester) {
        super(requester);
    }

    public String getStudentInfoPage(String cookies, RequestClientOption requestOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.studentInfoRequest(cookies);
        HttpResponse response = requester.get(request, requestOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }
}
