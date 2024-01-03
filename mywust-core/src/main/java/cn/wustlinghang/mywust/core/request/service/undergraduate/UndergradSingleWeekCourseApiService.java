package cn.wustlinghang.mywust.core.request.service.undergraduate;

import cn.wustlinghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

/**
 * <p>本科生单周课表页面获取</p>
 */
public class UndergradSingleWeekCourseApiService extends UndergradApiServiceBase {

    private static final String[] NECESSARY_PARAMS = {"date"};

    public UndergradSingleWeekCourseApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        for (String key : NECESSARY_PARAMS) {
            if (params.get(key) == null) {
                throw new ApiException(ApiException.Code.PARAM_WRONG_EXCEPTION);
            }
        }

        return this.getPage(params.get(NECESSARY_PARAMS[0]), cookie, option);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String date, String cookies, RequestClientOption requestClientOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.singleWeekCoursePageRequest(date, cookies);
        HttpResponse response = requester.post(request, requestClientOption);

        super.checkResponse(response);

        return response.getStringBody();
    }

    public String getPage(String date, String cookies) throws IOException, ApiException {
        return getPage(date, cookies, null);
    }
}
