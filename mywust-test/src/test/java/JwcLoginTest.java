import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.core.service.auth.JwcLogin;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

public class JwcLoginTest {
    @Test
    public void run() throws BasicException, IOException {
        System.out.println("bkjx登录测试（统一身份验证）");
        System.out.println("输入账号（学号）和密码，用“ ”（空格）分割");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String username = input.split(" ")[0];
        String password = input.split(" ")[1];

        System.out.println("账号：" + username);
        System.out.println("密码：" + password);

        Requester requester = new SimpleOkhttpRequester();
        JwcLogin jwcLogin = new JwcLogin(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setFallowUrlRedirect(false);
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setPort(6060);
        proxy.setAddress("127.0.0.1");
        option.setProxy(proxy);

        String cookies = jwcLogin.getLoginCookie(username, password, option);

        System.out.printf("获取到Cookies: %s \n", cookies);

        System.out.printf("检查Cookies: %s", jwcLogin.checkCookies(cookies, RequestClientOption.DEFAULT_OPTION));
    }
}
