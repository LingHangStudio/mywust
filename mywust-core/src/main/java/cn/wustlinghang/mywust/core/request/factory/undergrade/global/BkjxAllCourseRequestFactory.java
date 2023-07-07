package cn.wustlinghang.mywust.core.request.factory.undergrade.global;

import cn.wustlinghang.mywust.core.api.ConstantParams;
import cn.wustlinghang.mywust.core.api.UndergradUrls;
import cn.wustlinghang.mywust.core.request.factory.RequestFactory;
import cn.wustlinghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.wustlinghang.mywust.network.entitys.FormBodyBuilder;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;

public class BkjxAllCourseRequestFactory extends BkjxRequestFactory {
    public static HttpRequest classroomCoursePageRequest(String cookies, String term, String timeMode, String collegeId, String campusId, String buildingId, String classroomName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(13);
        formBodyBuilder
                .add("xnxqh", term)
                .add("kbjcmsid", timeMode == null ? ConstantParams.DEFAULT_TIME_MODEL : timeMode)
                // 上课学院，虽然能获取到，但是不符合预期的功能，得到的结果是这个学院上的课，而不是这个学院开的课
                .add("skyx", collegeId)
                .add("xqid", campusId)
                .add("jzwid", buildingId)
                .add("skjs", classroomName)
                .add("skjsid", "")
                .add("zc1", "")
                .add("zc2", "")
                .add("skxq1", "")
                .add("skxq2", "")
                .add("jc1", "")
                .add("jc2", "");

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_CLASSROOM_COURSE_API, formBodyBuilder.buildAndToString(), cookies);
    }

    public static HttpRequest teacherCoursePageRequest(String cookies, String term, String timeMode, String collegeId, String teacherName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(10);
        formBodyBuilder
                .add("xnxqh", term)
                .add("kbjcmsid", timeMode == null ? ConstantParams.DEFAULT_TIME_MODEL : timeMode)
                // 这里的学院其实不是真正开课的学院，观察发现其实是这个学院的老师上的课，但是课不一定是这个学院开的
                .add("skyx", collegeId)
                .add("skjs", teacherName)
                .add("zc1", "")
                .add("zc2", "")
                .add("skxq1", "")
                .add("skxq2", "")
                .add("jc1", "")
                .add("jc2", "");

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_TEACHER_COURSE_API, formBodyBuilder.buildAndToString(), cookies);
    }

    public static HttpRequest allCourseSchedulePageRequest(String cookies, String term, String timeMode, String subCollegeId, String courseName) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(15);
        formBodyBuilder
                .add("xnxqh", term)
                .add("kbjcmsid", timeMode == null ? ConstantParams.DEFAULT_TIME_MODEL : timeMode)
                // 上课学院，虽然能获取到，但是不符合预期的功能，得到的结果是这个学院上的课，而不是这个学院开的课
                .add("skyx", "")
                // 开课学院应该在这里指定，但是直接用学院id是获取不到的，只能用学院下级的id来获取，相关参数详见cn.wustlinghang.mywust.core.api.ConstantParams
                .add("kkyx", subCollegeId)
                .add("kcmc", courseName)
                .add("zzdKcSX", "")
                .add("kcid", "")
                .add("zc1", "")
                .add("zc1", "")
                .add("zc1", "")
                .add("zc2", "")
                .add("skxq1", "")
                .add("skxq2", "")
                .add("jc1", "")
                .add("jc2", "");

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_ALL_COURSE_SCHEDULE_API, formBodyBuilder.buildAndToString(), cookies);
    }
}
