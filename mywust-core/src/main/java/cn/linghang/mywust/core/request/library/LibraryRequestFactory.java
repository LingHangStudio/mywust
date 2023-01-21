package cn.linghang.mywust.core.request.library;

import cn.linghang.mywust.core.api.LibraryUrls;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;

public class LibraryRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(LibraryUrls.LIBRARY_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest indexRequest() {
        return makeHttpRequest(LibraryUrls.LIBRARY_INDEX_URL);
    }

    public static HttpRequest currentLoanRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_CURRENT_LOAN_API, null, cookie);
    }

    public static HttpRequest loanHistoryRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_LOAN_HISTORY_API, null, cookie);
    }

    public static HttpRequest overdueSoonRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_OVERDUE_SOON_API, null, cookie);
    }

    public static HttpRequest bookInfoRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_OVERDUE_SOON_API, null, cookie);
    }
}
