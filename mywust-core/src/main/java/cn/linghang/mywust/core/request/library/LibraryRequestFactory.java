package cn.linghang.mywust.core.request.library;

import cn.linghang.mywust.core.api.Library;
import cn.linghang.mywust.core.request.RequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;

public class LibraryRequestFactory extends RequestFactory {
    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(Library.LIBRARY_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest indexRequest() {
        return makeHttpRequest(Library.LIBRARY_INDEX_URL);
    }

    public static HttpRequest currentLoanRequest(String cookie) {
        return makeHttpRequest(Library.LIBRARY_CURRENT_LOAN_API, null, cookie);
    }

    public static HttpRequest loanHistoryRequest(String cookie) {
        return makeHttpRequest(Library.LIBRARY_LOAN_HISTORY_API, null, cookie);
    }

    public static HttpRequest overdueSoonRequest(String cookie) {
        return makeHttpRequest(Library.LIBRARY_OVERDUE_SOON_API, null, cookie);
    }
}
