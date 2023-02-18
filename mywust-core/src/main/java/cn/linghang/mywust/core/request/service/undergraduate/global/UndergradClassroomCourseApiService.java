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

public class UndergradClassroomCourseApiService extends UndergradApiServiceBase {
    public UndergradClassroomCourseApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        String term = params.get("term");
        String collegeId = params.get("college");
        String campusId = params.get("campus");
        String buildingId = params.get("building");
        String classroomName = params.get("classroomName");

        return this.getPage(cookie, term, collegeId, campusId, buildingId, classroomName, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, String term, String collegeId, String campusId, String buildingId, String classroomName, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.classroomCoursePageRequest(cookies, term, null, collegeId, campusId, buildingId, classroomName);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, String term, String collegeId, String campusId, String buildingId, String classroomName) throws ApiException, IOException {
        return this.getPage(cookies, term, collegeId, campusId, buildingId, classroomName, null);
    }
}
