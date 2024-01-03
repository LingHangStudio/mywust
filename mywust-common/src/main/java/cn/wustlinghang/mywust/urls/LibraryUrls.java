package cn.wustlinghang.mywust.urls;

public class LibraryUrls {
    public static final String LIBRARY_BASE_URL = "https://libsys.wust.edu.cn";

    public static final String LIBRARY_SESSION_COOKIE_API = LIBRARY_BASE_URL + "/meta-local/opac/cas/rosetta?ticket=%s";

    public static final String LIBRARY_INDEX_URL = LIBRARY_BASE_URL + "/meta-local/opac/cas/rosetta";

    // 用违章记录信息api地址作为测试url（大部分人应该没有违章吧 :）
    public static final String LIBRARY_COOKIE_TEST_URL = LIBRARY_BASE_URL + "/meta-local/opac/users/volts";

    public static final String LIBRARY_ACCOUNT_STATUS_API = LIBRARY_BASE_URL + "/meta-local/opac/users/stats";

    public static final String LIBRARY_CURRENT_LOAN_API = LIBRARY_BASE_URL + "/meta-local/opac/users/loans?page=%d&pageSize=%d";
    public static final String LIBRARY_LOAN_HISTORY_API = LIBRARY_BASE_URL + "/meta-local/opac/users/loan_hists?page=%d&pageSize=%d";
    public static final String LIBRARY_OVERDUE_SOON_API = LIBRARY_BASE_URL + "/meta-local/opac/users/overdue_soon?page=%d&pageSize=%d";

    public static final String LIBRARY_BOOK_INFO_API = LIBRARY_BASE_URL + "/meta-local/opac/bibs/%s/infos";
    public static final String LIBRARY_BOOK_DOUBAN_INFO_API = LIBRARY_BASE_URL + "/meta-local/opac/third_api/douban/%s/info";
    public static final String LIBRARY_BOOK_CONTENT_API = LIBRARY_BASE_URL + "/meta-local/opac/bibs/%s/quoteds/quoted.caj-cd/content";
    public static final String LIBRARY_BOOK_HOLDING_API = LIBRARY_BASE_URL + "/meta-local/opac/bibs/%s/holdings";

    public static final String LIBRARY_BOOK_COVER_IMAGE_API = LIBRARY_BASE_URL + "/meta-local/opac/search/extend/";

    public static final String LIBRARY_SEARCH_API = LIBRARY_BASE_URL + "/meta-local/opac/search/";
}
