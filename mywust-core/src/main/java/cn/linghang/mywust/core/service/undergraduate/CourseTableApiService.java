package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.CourseTableParser;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.model.global.Course;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class CourseTableApiService extends UndergraduateApiService {

    public CourseTableApiService(Requester requester) {
        super(requester);
    }

    public String getCourseTablePage(String term, String cookies, RequestClientOption requestClientOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.courseTablePageRequest(term, cookies);
        HttpResponse response = requester.post(request, requestClientOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }

}
