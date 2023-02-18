package cn.linghang.mywust.core.service.physics;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.physics.PhysicsSystemRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class PhysicsCourseApiServiceBase extends PhysicsApiServiceBase {
    public PhysicsCourseApiServiceBase(Requester requester) {
        super(requester);
    }

    public String getPage(String cookie, RequestClientOption requestClientOption) throws IOException, ApiException {
        requestClientOption.setFallowUrlRedirect(false);

        // 直接请求真正的课表页
        HttpRequest coursePageRequest = PhysicsSystemRequestFactory.physicsCourseRequest(cookie);
        HttpResponse courseResponse = requester.get(coursePageRequest, requestClientOption);
        checkResponse(courseResponse);

        return new String(courseResponse.getBody());
    }
}