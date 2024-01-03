package cn.wustlinghang.mywust.core.request.service.undergraduate.school;

import cn.wustlinghang.mywust.core.request.factory.undergrade.global.BkjxAllCourseRequestFactory;
import cn.wustlinghang.mywust.core.request.service.undergraduate.UndergradApiServiceBase;
import cn.wustlinghang.mywust.data.undergrad.params.ClassroomCourseGettingParam;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * <p>教室课表查询，对应的实际接口为『教室课表查询』</p>
 * <p>由于该接口获取的页面数据中，课程名和教师名黏在了一起，不易分离，因此基本上不使用该接口来获取课程信息，而是仅用于获取教室空闲信息，而不用于获取课程信息</p>
 * <p>若要获取全校学院和老师的课表详细信息，请见{@link UndergradAllCourseScheduleApiService}</p>
 */
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

        ClassroomCourseGettingParam param = new ClassroomCourseGettingParam();
        param.setTerm(term);
        param.setCollegeId(collegeId);
        param.setCampusId(campusId);
        param.setBuildingId(buildingId);
        param.setClassroomName(classroomName);

        return this.getPage(cookie, param, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, ClassroomCourseGettingParam param, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.classroomCoursePageRequest(cookies, param);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, ClassroomCourseGettingParam param) throws ApiException, IOException {
        return this.getPage(cookies, param, null);
    }
}
