package cn.wustlinghang.mywust.core.request.service.undergraduate;

import cn.wustlinghang.mywust.urls.UndergradUrls;
import cn.wustlinghang.mywust.core.util.BkjxUtil;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.network.request.RequestFactory;

import java.io.IOException;
import java.util.Map;

public abstract class UndergradApiServiceBase {
    protected final Requester requester;

    public UndergradApiServiceBase(Requester requester) {
        this.requester = requester;
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        boolean cookieInvalid = response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                BkjxUtil.needLogin(response.getBody());
        if (cookieInvalid) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        } else if (BkjxUtil.isBannedResponse(response)) {
            throw new ApiException(ApiException.Code.UNDERGRAD_BANNED_IN_EXCLUSIVE_TIME);
        }
    }

    public void checkCookies(String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = RequestFactory.makeHttpRequest(UndergradUrls.BKJX_TEST_API, null, cookie);
        HttpResponse response = requester.get(request, option);

        this.checkResponse(response);
    }

    public void checkCookies(String cookie) throws ApiException, IOException {
        this.checkCookies(cookie, null);
    }

    public abstract String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException;

    public abstract String getPage(String cookie, Map<String, String> params) throws ApiException, IOException;
}
