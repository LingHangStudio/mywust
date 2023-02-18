package cn.linghang.mywust.core.request.service.graduate;

import cn.linghang.mywust.core.api.GraduateUrls;
import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.factory.RequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class GraduateApiServiceBase {
    protected final Requester requester;

    public GraduateApiServiceBase(Requester requester) {
        this.requester = requester;
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                new String(response.getBody()).contains("name=\"_ctl0:txtpassword\"")) {

            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }

    public void checkCookies(String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = RequestFactory.makeHttpRequest(GraduateUrls.GRADUATE_INDEX_TEST_API, null, cookie);
        HttpResponse response = requester.get(request, option);

        this.checkResponse(response);
    }

    public void checkCookies(String cookie) throws ApiException, IOException {
        this.checkCookies(cookie, null);
    }
}
