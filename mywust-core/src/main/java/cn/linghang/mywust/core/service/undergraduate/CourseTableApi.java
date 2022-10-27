package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.CourseTableParser;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.core.util.BkjxUtil;
import cn.linghang.mywust.model.global.Course;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class CourseTableApi extends UndergraduateApi {
    private static final Logger log = LoggerFactory.getLogger(CourseTableApi.class);

    private static final CourseTableParser parser = new CourseTableParser();

    private final Requester requester;

    public CourseTableApi(Requester requester) {
        this.requester = requester;
    }

    public String getCourseTablePage(String term, String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException {
        HttpRequest request = BkjxRequestFactory.courseTablePageRequest(term, cookies);
        HttpResponse response = requester.post(request, requestClientOption);

        checkResponse(response, cookies);

        return new String(response.getBody());
    }

    public List<Course> getCourseTable(String term, String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException, ParseException {
        String page = this.getCourseTablePage(term, cookies, requestClientOption);
        return parser.parse(page);
    }
}
