package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class SearchApiService extends BaseLibraryApiService {
    public SearchApiService(Requester requester) {
        super(requester);
    }

    public String search(String keyword, int page, int pageSize) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookSearchRequest(keyword, page, pageSize);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return response.getStringBody();
    }

}
