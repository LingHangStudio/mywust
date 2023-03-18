package cn.linghang.mywust.core.request.service.library;

import cn.linghang.mywust.exception.ApiException;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpResponse;

public abstract class LibraryApiServiceBase {
    protected final Requester requester;

    public LibraryApiServiceBase(Requester requester) {
        this.requester = requester;
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        if (response.getStatusCode() != 200) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }
}
