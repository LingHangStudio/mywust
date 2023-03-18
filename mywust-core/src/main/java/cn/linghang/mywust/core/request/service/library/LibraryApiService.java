package cn.linghang.mywust.core.request.service.library;

import cn.linghang.mywust.exception.ApiException;
import cn.linghang.mywust.core.request.factory.library.LibraryRequestFactory;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

/**
 * 图书馆相关接口，由于返回的数据都是json，特别好解析，所以这里的数据获取到了之后可以直接拿来用，自己来解析
 */
public class LibraryApiService extends LibraryApiServiceBase {
    public LibraryApiService(Requester requester) {
        super(requester);
    }

    public String search(String keyword, int page, int pageSize) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookSearchRequest(keyword, page, pageSize);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }

    public String getBookDetail(String bookId) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookInfoRequest(bookId);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }

    public String getBookCoverImageUrl(String isbn) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.bookCoverImageUrlRequest(isbn);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }

    public String getOverdueSoon(String cookie) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.overdueSoonRequest(cookie);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }

    public String getCurrentLoan(String cookie) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.currentLoanRequest(cookie);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }

    public String getLoanHistory(String cookie) throws ApiException, IOException {
        HttpRequest request = LibraryRequestFactory.loanHistoryRequest(cookie);
        HttpResponse response = requester.post(request);
        checkResponse(response);

        return request.getStringData();
    }
}