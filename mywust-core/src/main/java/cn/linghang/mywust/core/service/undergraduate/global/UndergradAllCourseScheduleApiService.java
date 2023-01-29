package cn.linghang.mywust.core.service.undergraduate.global;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.global.BkjxAllCourseRequestFactory;
import cn.linghang.mywust.core.service.undergraduate.UndergradApiServiceBase;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UndergradAllCourseScheduleApiService extends UndergradApiServiceBase {
    public UndergradAllCourseScheduleApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        String term = params.get("term");
        String timeMode = params.get("timeMode");
        String subCollegeId = params.get("subCollege");
        String courseName = params.get("courseName");

        return this.getPage(cookie, term, timeMode, subCollegeId, courseName, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, String term, String timeMode, String subCollegeId, String courseName, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.allCourseSchedulePageRequest(cookies, term, timeMode, subCollegeId, courseName);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, String term, String timeMode, String subCollegeId, String courseName) throws ApiException, IOException {
        return this.getPage(cookies, term, timeMode, subCollegeId, courseName, null);
    }
}
