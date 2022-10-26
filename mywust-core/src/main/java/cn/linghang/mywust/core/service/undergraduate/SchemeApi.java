package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.SchemePageParser;
import cn.linghang.mywust.core.request.BkjxRequestFactory;
import cn.linghang.mywust.core.util.BkjxUtil;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class SchemeApi {
    private final Requester requester;

    private static final SchemePageParser parser = new SchemePageParser();

    public SchemeApi(Requester requester) {
        this.requester = requester;
    }

    public String getSchemePage(String cookies, RequestClientOption requestClientOption) throws CookieInvalidException, IOException {
        HttpRequest request = BkjxRequestFactory.schemePageRequest(cookies);
        HttpResponse response = requester.get(request, requestClientOption);

        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                BkjxUtil.checkLoginFinger(response.getBody())) {

            throw new CookieInvalidException("[请求获取成绩]：Cookie无效：" + cookies);
        }

        return new String(response.getBody());
    }

    public String getPrueSchemePage(String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException, ParseException {
        String fullPage = this.getSchemePage(cookies, requestClientOption);
        return parser.parse(fullPage);
    }
}
