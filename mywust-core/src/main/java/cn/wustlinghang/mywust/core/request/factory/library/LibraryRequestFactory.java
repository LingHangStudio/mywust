package cn.wustlinghang.mywust.core.request.factory.library;

import cn.wustlinghang.mywust.core.api.LibraryUrls;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.data.library.origin.BookSearchRequest;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.StringJoiner;

@Slf4j
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
        return bookSearchRequest(new BookSearchRequest(keyword, pageSize, page));
    }

    public static HttpRequest bookSearchRequest(BookSearchRequest request) {
        return makeHttpRequest(LibraryUrls.LIBRARY_SEARCH_API, json(request))
                .addHeaders("Content-Type", "application/json");
    }

    public static HttpRequest accountStateRequest(String cookie) {
        return makeHttpRequest(LibraryUrls.LIBRARY_ACCOUNT_STATUS_API, null, cookie);
    }

    public static HttpRequest currentLoanRequest(String cookie, int page, int pageSize) {
        return makeHttpRequest(String.format(LibraryUrls.LIBRARY_CURRENT_LOAN_API, page, pageSize), null, cookie);
    }

    public static HttpRequest loanHistoryRequest(String cookie, int page, int pageSize) {
        return makeHttpRequest(String.format(LibraryUrls.LIBRARY_LOAN_HISTORY_API, page, pageSize), null, cookie);
    }

    public static HttpRequest overdueSoonRequest(String cookie, int page, int pageSize) {
        return makeHttpRequest(String.format(LibraryUrls.LIBRARY_OVERDUE_SOON_API, page, pageSize), null, cookie);
    }

    public static HttpRequest bookInfoRequest(String bookId) {
        String url = String.format(LibraryUrls.LIBRARY_BOOK_INFO_API, bookId);
        return makeHttpRequest(url, (byte[]) null);
    }

    public static HttpRequest bookDoubanInfoRequest(String isbn) {
        String url = String.format(LibraryUrls.LIBRARY_BOOK_DOUBAN_INFO_API, isbn);
        return makeHttpRequest(url, (byte[]) null);
    }

    public static HttpRequest bookContentRequest(String id) {
        String url = String.format(LibraryUrls.LIBRARY_BOOK_CONTENT_API, id);
        return makeHttpRequest(url, (byte[]) null);
    }

    public static HttpRequest bookHoldingRequest(String bookId) {
        String url = String.format(LibraryUrls.LIBRARY_BOOK_HOLDING_API, bookId);
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
        } catch (JsonProcessingException e) {
            log.error("生成json时出现不应出现的异常：", e);
            return "";
        }
    }
}
