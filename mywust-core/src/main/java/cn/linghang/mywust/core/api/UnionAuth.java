package cn.linghang.mywust.core.api;

public class UnionAuth {
    /**
     * 统一认证登录验证的api地址，请求之后可进一步获取service ticket来对具体的服务进行登录获取Cookies
     */
    public static final String UNION_AUTH_API = "https://auth.wust.edu.cn/lyuapServer/v1/tickets";
}