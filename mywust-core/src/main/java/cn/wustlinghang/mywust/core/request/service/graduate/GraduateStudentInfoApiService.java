package cn.wustlinghang.mywust.core.request.service.graduate;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.core.request.factory.graduate.GraduateRequestFactory;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class GraduateStudentInfoApiService extends GraduateApiServiceBase {

    public GraduateStudentInfoApiService(Requester requester) {
        super(requester);
    }

    public String getPage(String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = GraduateRequestFactory.studentInfoRequest(cookie);
        request.addHeaders("Referer", "http://59.68.177.189/pyxx/leftmenu.aspx");

        HttpResponse response = requester.get(request, option);
        super.checkResponse(response);

        return response.getStringBody();
    }
}
