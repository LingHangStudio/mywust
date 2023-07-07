package cn.wustlinghang.mywust.core.api;

public class UnionAuthUrls {
    /**
     * 统一认证登录验证的api地址，请求之后可进一步获取service ticket来对具体的服务进行登录获取Cookies
     */
    public static final String UNION_AUTH_API = "https://auth.wust.edu.cn/lyuapServer/v1/tickets";

    public static class Service {
        public static final String LIBRARY_SSO_SERVICE = "https://libsys.wust.edu.cn:443/meta-local/opac/cas/rosetta";

        public static final String BKJX_SSO_SERVICE = "http://bkjx.wust.edu.cn/jsxsd/sso.jsp";

        public static final String PORTAL_SSO_SERVICE = "https://portal.wust.edu.cn/c/portal/login?redirect=%2F&p_l_id=121585";
    }
}
