package cn.wustlinghang.mywust.core.request.service.library;

import cn.wustlinghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class BookDetailApiService extends BaseLibraryApiService {
    public BookDetailApiService(Requester requester) {
        super(requester);
    }

    public String getBookDetail(String bookId) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookInfoRequest(bookId);
        HttpResponse response = requester.get(request);
        checkResponse(response);

        return response.getStringBody();
    }

}
