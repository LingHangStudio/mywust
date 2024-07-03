package cn.wustlinghang.mywust.urls;

public class UnionAuthUrls {
    public static final String UNION_AUTH_BASE_URL = "https://auth.wust.edu.cn";

    /**
     * 统一认证登录验证的api地址，请求之后可进一步获取service ticket来对具体的服务进行登录获取Cookies
     */
    public static final String UNION_AUTH_API = UNION_AUTH_BASE_URL +"/lyuapServer/v1/tickets";

    /**
     * 验证码api地址
     */
    public static final String UNION_AUTH_CAPTCHA_API = UNION_AUTH_BASE_URL +"/lyuapServer/kaptcha?_t=%d&uid=%s";

    public static class Service {
        public static final String LIBRARY_SSO_SERVICE = "https://libsys.wust.edu.cn:443/meta-local/opac/cas/rosetta";

        public static final String BKJX_SSO_SERVICE = "https://bkjx.wust.edu.cn/jsxsd/";

        public static final String PORTAL_SSO_SERVICE = "https://portal.wust.edu.cn/c/portal/login?redirect=%2F&p_l_id=121585";
    }
}
