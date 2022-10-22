import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.core.service.auth.JwcLogin;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.Scanner;

public class JwcLegacyLoginTest {
    public static void main(String[] args) throws BasicException, IOException {
        new JwcLegacyLoginTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("bkjx旧版直登测试");
        System.out.println("输入账号（学号）和密码，用“ ”（空格）分割");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String username = input.split(" ")[0];
        String password = input.split(" ")[1];

        System.out.println("账号：" + username);
        System.out.println("密码：" + password);

        Requester requester = new SimpleOkhttpRequester();
        JwcLogin jwcLogin = new JwcLogin(requester, new UnionLogin(requester));

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        String cookies = jwcLogin.getLoginCookieLegacy(username, password, option);

        System.out.println(cookies);
    }
}
