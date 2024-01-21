package cn.wustlinghang.mywust.core.request.service.undergraduate.school;

import cn.wustlinghang.mywust.urls.ConstantParams;
import cn.wustlinghang.mywust.data.undergrad.params.AllCourseScheduleGettingParam;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.core.request.factory.undergrade.school.BkjxAllCourseRequestFactory;
import cn.wustlinghang.mywust.core.request.service.undergraduate.UndergradApiServiceBase;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * <p>全校课程课表查询，对应于官网的『课程课表查询』。</p>
 * <p>主要用于全校的按子学院的课表详情查询</p>
 * <p>如需获取全部课程的信息，一般用法为遍历{@link ConstantParams}中的<code>SUB_COLLEGE_ID_LIST</code>来不断查询获取。</p>
 */
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

        AllCourseScheduleGettingParam param = new AllCourseScheduleGettingParam();
        param.setTerm(term);
        param.setTimeMode(timeMode);
        param.setSubCollegeId(subCollegeId);
        param.setCourseName(courseName);

        return this.getPage(cookie, param, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookies, AllCourseScheduleGettingParam param, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = BkjxAllCourseRequestFactory.allCourseSchedulePageRequest(cookies, param);
        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String cookies, AllCourseScheduleGettingParam param) throws ApiException, IOException {
        return this.getPage(cookies, param, null);
    }
}
