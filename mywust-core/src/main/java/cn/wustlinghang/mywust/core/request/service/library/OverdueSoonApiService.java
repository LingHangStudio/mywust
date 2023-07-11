package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class OverdueSoonApiService extends BaseLibraryApiService {
    public OverdueSoonApiService(Requester requester) {
        super(requester);
    }

    public String getOverdueSoon(String cookie) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.overdueSoonRequest(cookie);
        HttpResponse response = requester.get(request);
        checkResponse(response);

        return response.getStringBody();
    }
}
