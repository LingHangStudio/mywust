package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.StudentInfoPageParser;
import cn.linghang.mywust.core.request.BkjxRequestFactory;
import cn.linghang.mywust.core.util.BkjxUtil;
import cn.linghang.mywust.model.undergrade.StudentInfo;
import cn.linghang.mywust.network.HttpRequest;
import cn.linghang.mywust.network.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class JwcService {
    private static final Logger log = LoggerFactory.getLogger(JwcService.class);

    private final Requester requester;

    private final StudentInfoPageParser studentInfoPageParser;

    public JwcService(Requester requester, StudentInfoPageParser studentInfoPageParser) {
        this.requester = requester;
        this.studentInfoPageParser = studentInfoPageParser;
    }

    public String getStudentInfoPage(String cookies, RequestClientOption requestOption) throws IOException, CookieInvalidException {
        HttpRequest request = BkjxRequestFactory.studentInfoRequest(cookies);
        HttpResponse response = requester.get(request, requestOption);

        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                BkjxUtil.checkLoginFinger(response.getBody())) {

            throw new CookieInvalidException();
        }

        return new String(response.getBody());
    }

    public StudentInfo getStudentInfo(String cookies, RequestClientOption requestOption) throws IOException, CookieInvalidException, ParseException {
        String studentInfoPage = this.getStudentInfoPage(cookies, requestOption);

        return studentInfoPageParser.parse(studentInfoPage);
    }
}
