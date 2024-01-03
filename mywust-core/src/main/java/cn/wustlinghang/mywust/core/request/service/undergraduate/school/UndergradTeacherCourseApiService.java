package cn.wustlinghang.mywust.core.request.service.undergraduate.school;

import cn.wustlinghang.mywust.core.request.factory.undergrade.global.BkjxAllCourseRequestFactory;
import cn.wustlinghang.mywust.core.request.service.undergraduate.UndergradApiServiceBase;
import cn.wustlinghang.mywust.data.undergrad.params.TeacherCourseGettingParam;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * <p>教师课表查询，对应于官网中的『教师课表查询』。</p>
 * <p>
 * 常用于按某个学院的老师的课表查询。于{@link UndergradAllCourseScheduleApiService}的区别主要在于此处的教师字段为单个老师，
 * 而UndergradAllCourseScheduleApiService的教师字段是用逗号隔开的。
 * </p>
 * <p>另一个问题就是，此处的学院和实际的开课学院会有一些出入。这里所谓的“开课学院”其实并不是真正的“开课学院”，而是这个学院的老师教的课</p>
 */
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

        TeacherCourseGettingParam param = new TeacherCourseGettingParam();
        param.setTerm(term);
        param.setTimeMode(timeMode);
        param.setCollegeId(collegeId);
        param.setTeacherName(teacherName);

        return this.getPage(cookie, param, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, TeacherCourseGettingParam param, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.teacherCoursePageRequest(cookies, param);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, TeacherCourseGettingParam param) throws ApiException, IOException {
        return this.getPage(cookies, param, null);
    }
}
