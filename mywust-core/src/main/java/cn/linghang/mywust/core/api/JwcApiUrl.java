package cn.linghang.mywust.core.api;

import lombok.Getter;

@Getter
public class JwcApiUrl {
    /**
     * 统一认证登录验证的api地址，请求之后可进一步获取ticket
     */
    public static final String UNION_AUTH_API = "https://auth.wust.edu.cn/lyuapServer/v1/tickets";

    public static final String JWC_SSO_SERVICE = "http://bkjx.wust.edu.cn/jsxsd/sso.jsp";
    public static final String JWC_TICKET_API = "http://bkjx.wust.edu.cn/jsxsd/sso.jsp?ticket=%s";

}
