package cn.wustlinghang.mywust.core.request.service.physics;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

public abstract class PhysicsApiServiceBase {
    protected final Requester requester;

    public PhysicsApiServiceBase(Requester requester) {
        this.requester = requester;
    }

    protected void checkResponse(HttpResponse response) throws ApiException {
        if (response.getStatusCode() != HttpResponse.HTTP_OK) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }
}
