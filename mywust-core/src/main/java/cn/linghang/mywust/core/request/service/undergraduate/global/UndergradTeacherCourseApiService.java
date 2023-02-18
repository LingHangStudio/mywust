package cn.linghang.mywust.core.request.service.undergraduate.global;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.factory.undergrade.global.BkjxAllCourseRequestFactory;
import cn.linghang.mywust.core.request.service.undergraduate.UndergradApiServiceBase;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UndergradTeacherCourseApiService extends UndergradApiServiceBase {
    public UndergradTeacherCourseApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        String term = params.get("term");
        String timeMode = params.get("timeMode");
        String collegeId = params.get("collegeId");
        String teacherName = params.get("teacherName");

        return this.getPage(cookie, term, timeMode, collegeId, teacherName, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, String term, String timeMode, String collegeId, String teacherName, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.teacherCoursePageRequest(cookies, term, timeMode, collegeId, teacherName);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, String term, String timeMode, String collegeId, String teacherName) throws ApiException, IOException {
        return this.getPage(cookies, term, timeMode, collegeId, teacherName, null);
    }
}
