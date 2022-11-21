package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.StudentInfoPageParser;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.model.global.StudentInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class StudentInfoApi extends UndergraduateApi {
    private final Requester requester;

    private static final StudentInfoPageParser parser = new StudentInfoPageParser();

    public StudentInfoApi(Requester requester) {
        this.requester = requester;
    }

    public String getStudentInfoPage(String cookies, RequestClientOption requestOption) throws IOException, CookieInvalidException {
        HttpRequest request = BkjxRequestFactory.studentInfoRequest(cookies);
        HttpResponse response = requester.get(request, requestOption);

        checkResponse(response, cookies);

        return new String(response.getBody());
    }

    public StudentInfo getStudentInfo(String cookies, RequestClientOption requestOption) throws IOException, CookieInvalidException, ParseException {
        String studentInfoPage = this.getStudentInfoPage(cookies, requestOption);

        return parser.parse(studentInfoPage);
    }
}
