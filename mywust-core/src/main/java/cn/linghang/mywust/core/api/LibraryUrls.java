package cn.linghang.mywust.core.api;

public class LibraryUrls {
    public static final String LIBRARY_SESSION_COOKIE_API = "https://libsys.wust.edu.cn/meta-local/opac/cas/rosetta?ticket=%s";

    public static final String LIBRARY_INDEX_URL = "https://libsys.wust.edu.cn/meta-local/opac/cas/rosetta";

    public static final String LIBRARY_COOKIE_TEST_URL = "https://libsys.wust.edu.cn/meta-local/opac/users/info";

    public static final String LIBRARY_ACCOUNT_STATUS_API = "https://libsys.wust.edu.cn/meta-local/opac/users/stats";

    public static final String LIBRARY_CURRENT_LOAN_API = "https://libsys.wust.edu.cn/meta-local/opac/users/loans?page=1&pageSize=100";
    public static final String LIBRARY_LOAN_HISTORY_API = "https://libsys.wust.edu.cn/meta-local/opac/users/loan_hists?page=1&pageSize=100";
    public static final String LIBRARY_OVERDUE_SOON_API = "https://libsys.wust.edu.cn/meta-local/opac/users/overdue_soon";

    public static final String LIBRARY_BOOK_INFO_API = "https://libsys.wust.edu.cn/meta-local/opac/bibs/%s/infos";

    public static final String LIBRARY_BOOK_COVER_IMAGE_API = "https://libsys.wust.edu.cn/meta-local/opac/search/extend/";

    public static final String LIBRARY_SEARCH_API = "https://libsys.wust.edu.cn/meta-local/opac/search/";
}
