package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

public abstract class BaseLibraryApiService {
    protected final Requester requester;

    public BaseLibraryApiService(Requester requester) {
        this.requester = requester;
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        if (response.getStatusCode() != 200) {
            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }
}
