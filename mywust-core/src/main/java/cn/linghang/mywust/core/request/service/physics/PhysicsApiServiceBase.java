package cn.linghang.mywust.core.request.service.physics;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpResponse;

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
