package cn.linghang.mywust.core.request.factory.library;

import cn.hutool.core.util.URLUtil;
import cn.linghang.mywust.core.api.LibraryUrls;
import cn.linghang.mywust.core.request.factory.RequestFactory;
import cn.linghang.mywust.core.request.factory.library.request.SearchRequest;
import cn.linghang.mywust.network.entitys.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.StringJoiner;

public class LibraryRequestFactory extends RequestFactory {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private static final String searchTemplate = "{\"page\":%d,\"pageSize\":%d,\"indexName\":\"idx.opac\",\"sortField\":\"relevance\",\"sortType\":\"desc\",\"collapseField\":\"groupId\",\"queryFieldList\":[{\"logic\":0,\"field\":\"all\",\"values\":[\"%s\"],\"operator\":\"*\"}],\"filterFieldList\":[]}";

    public static HttpRequest sessionCookieRequest(String serviceTicket) {
        return makeHttpRequest(String.format(LibraryUrls.LIBRARY_SESSION_COOKIE_API, serviceTicket));
    }

    public static HttpRequest indexRequest() {
        return makeHttpRequest(LibraryUrls.LIBRARY_INDEX_URL);
    }

    public static HttpRequest bookSearchRequest(String keyword, int page, int pageSize) {
        return bookSearchRequest(new SearchRequest(keyword, page, pageSize));
    }

    public static HttpRequest bookSearchRequest(SearchRequest request) {
        return makeHttpRequest(LibraryUrls.LIBRARY_SEARCH_API, json(request))
                .addHeaders("Content-Type", "application/json");
    }

    public static HttpRequest accountStateRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_ACCOUNT_STATUS_API, null, cookie);
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

    public static HttpRequest bookInfoRequest(String bookId) {
        String url = String.format(LibraryUrls.LIBRARY_BOOK_INFO_API, bookId);
        return makeHttpRequest(url, (byte[]) null);
    }

    public static HttpRequest bookCoverImageUrlRequest(List<String> isbn) {
        StringJoiner joiner = new StringJoiner("%2C", LibraryUrls.LIBRARY_BOOK_COVER_IMAGE_API, "");
        isbn.forEach(joiner::add);

        return makeHttpRequest(joiner.toString(), (byte[]) null);
    }

    public static HttpRequest bookCoverImageUrlRequest(String isbn) {
        return makeHttpRequest(LibraryUrls.LIBRARY_BOOK_COVER_IMAGE_API + isbn, (byte[]) null);
    }

    private static String json(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException ignored) {
            return "";
        }
    }
}
