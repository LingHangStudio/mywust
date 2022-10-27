package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.util.BkjxUtil;
import cn.linghang.mywust.network.entitys.HttpResponse;

public class UndergraduateApi {
    public void checkResponse(HttpResponse response, String cookies) throws CookieInvalidException {
        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                BkjxUtil.checkLoginFinger(response.getBody())) {

            throw new CookieInvalidException("响应无效，cookie可能过期：" + cookies);
        }
    }
}
