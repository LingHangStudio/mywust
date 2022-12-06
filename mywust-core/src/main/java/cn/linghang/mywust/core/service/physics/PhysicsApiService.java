package cn.linghang.mywust.core.service.physics;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.physics.PhysicsSystemRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class PhysicsApiService {
    private final Requester requester;

    public PhysicsApiService(Requester requester) {
        this.requester = requester;
    }

    public String getCoursePage(String cookie, RequestClientOption requestClientOption) throws IOException, ApiException {
        requestClientOption.setFallowUrlRedirect(false);

        // 请求真正的课表页
        HttpRequest coursePageRequest = PhysicsSystemRequestFactory.physicsCourseRequest(cookie);
        HttpResponse courseResponse = requester.get(coursePageRequest, requestClientOption);
        if (courseResponse.getStatusCode() != HttpResponse.HTTP_OK) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }

        return new String(courseResponse.getBody());
    }
}
