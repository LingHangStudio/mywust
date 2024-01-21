package cn.wustlinghang.mywust.core.request.factory.undergrade.school;

import cn.wustlinghang.mywust.urls.ConstantParams;
import cn.wustlinghang.mywust.urls.UndergradUrls;
import cn.wustlinghang.mywust.data.undergrad.params.AllCourseScheduleGettingParam;
import cn.wustlinghang.mywust.data.undergrad.params.ClassroomCourseGettingParam;
import cn.wustlinghang.mywust.data.undergrad.params.TeacherCourseGettingParam;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.wustlinghang.mywust.network.entitys.FormBodyBuilder;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;

public class BkjxAllCourseRequestFactory extends BkjxRequestFactory {
    public static HttpRequest classroomCoursePageRequest(String cookies, ClassroomCourseGettingParam param) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(13);
        formBodyBuilder
                .add("xnxqh", param.getTerm())
                .add("kbjcmsid", timeModeOrDefault(param.getTimeMode()))
                // 上课学院，虽然能获取到，但是不符合预期的功能，得到的结果是这个学院上的课，而不是这个学院开的课
                .add("skyx", param.getCollegeId())
                .add("xqid", param.getCampusId())
                .add("jzwid",param.getBuildingId())
                .add("skjs", param.getClassroomName())
                .add("skjsid", "")
                .add("zc1", "")
                .add("zc2", "")
                .add("skxq1", "")
                .add("skxq2", "")
                .add("jc1", "")
                .add("jc2", "");

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_CLASSROOM_COURSE_API, formBodyBuilder.buildString(), cookies);
    }

    public static HttpRequest teacherCoursePageRequest(String cookies, TeacherCourseGettingParam param) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(10);
        formBodyBuilder
                .add("xnxqh", param.getTerm())
                .add("kbjcmsid", timeModeOrDefault(param.getTimeMode()))
                // 这里的学院其实不是真正开课的学院，观察发现其实是这个学院的老师上的课，但是课不一定是这个学院开的
                .add("skyx", param.getCollegeId())
                .add("skjs", param.getTeacherName())
                .add("zc1", "")
                .add("zc2", "")
                .add("skxq1", "")
                .add("skxq2", "")
                .add("jc1", "")
                .add("jc2", "");

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_TEACHER_COURSE_API, formBodyBuilder.buildString(), cookies);
    }

    public static HttpRequest allCourseSchedulePageRequest(String cookies, AllCourseScheduleGettingParam param) {
        FormBodyBuilder formBodyBuilder = new FormBodyBuilder(15);
        formBodyBuilder
                .add("xnxqh", param.getTerm())
                .add("kbjcmsid", timeModeOrDefault(param.getTimeMode()))
                // 上课学院，虽然能获取到，但是不符合预期的功能，得到的结果是这个学院上的课，而不是这个学院开的课
                .add("skyx", "")
                // 开课学院应该在这里指定，但是直接用学院id是获取不到的，只能用学院下级的id来获取，相关参数详见cn.wustlinghang.mywust.core.api.ConstantParams
                .add("kkyx", param.getSubCollegeId())
                .add("kcmc", param.getCourseName())
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

        return RequestFactory.makeStringDataHttpRequest(UndergradUrls.BKJX_ALL_COURSE_SCHEDULE_API, formBodyBuilder.buildString(), cookies);
    }
    
    private static String timeModeOrDefault(String timeMode) {
        if (timeMode == null || timeMode.isEmpty()) {
            return ConstantParams.DEFAULT_TIME_MODEL;
        }
        
        return timeMode;
    }
}
