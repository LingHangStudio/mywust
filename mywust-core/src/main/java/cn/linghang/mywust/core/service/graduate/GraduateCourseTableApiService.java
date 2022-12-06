package cn.linghang.mywust.core.service.graduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.graduate.GraduateRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class GraduateCourseTableApiService extends GraduateApiService{

    public GraduateCourseTableApiService(Requester requester) {
        super(requester);
    }

    public String getCourseTablePage(String cookie, RequestClientOption option) throws IOException, ApiException {
        HttpRequest request = GraduateRequestFactory.courseTableRequest(cookie);
        HttpResponse response = requester.get(request, option);
        super.checkResponse(response);

        return new String(response.getBody());
    }
}
