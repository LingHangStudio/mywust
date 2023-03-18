package cn.linghang.mywust.core.request.service.graduate;

import cn.linghang.mywust.exception.ApiException;
import cn.linghang.mywust.core.request.factory.graduate.GraduateRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class GraduateScoreApiService extends GraduateApiServiceBase {

    public GraduateScoreApiService(Requester requester) {
        super(requester);
    }

    public String getPage(String cookie, RequestClientOption option) throws IOException, ApiException {
        HttpRequest request = GraduateRequestFactory.examScoreInfoRequest(cookie);
        request.addHeaders("Referer", "http://59.68.177.189/pyxx/leftmenu.aspx");

        HttpResponse response = requester.get(request, option);
        super.checkResponse(response);

        return new String(response.getBody());
    }
}
