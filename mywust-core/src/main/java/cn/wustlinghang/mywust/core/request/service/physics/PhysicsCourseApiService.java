package cn.wustlinghang.mywust.core.request.service.physics;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.core.request.factory.physics.PhysicsSystemRequestFactory;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class PhysicsCourseApiService extends PhysicsApiServiceBase {
    public PhysicsCourseApiService(Requester requester) {
        super(requester);
    }

    public String getPage(String cookie, RequestClientOption requestClientOption) throws IOException, ApiException {
        RequestClientOption tmpOption = requestClientOption.copy();
        tmpOption.setFollowUrlRedirect(false);

        // 直接请求真正的课表页
        HttpRequest coursePageRequest = PhysicsSystemRequestFactory.physicsCourseRequest(cookie);
        HttpResponse courseResponse = requester.get(coursePageRequest, tmpOption);
        checkResponse(courseResponse);

        return new String(courseResponse.getBody());
    }
}
