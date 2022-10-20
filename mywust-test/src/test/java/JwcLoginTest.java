import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.undergraduate.JwcLogin;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import cn.linghang.mywust.util.PasswordEncoder;

import java.io.IOException;
import java.util.Scanner;

public class JwcLoginTest {
    public static void main(String[] args) throws BasicException, IOException {
        new JwcLoginTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("输入账号（学号）和密码，用“ ”（空格）分割");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String username = input.split(" ")[0];
        String password = input.split(" ")[1];

        System.out.println("账号：" + username);
        System.out.println("密码：" + password);

        JwcLogin jwcLogin = new JwcLogin(new SimpleOkhttpRequester());

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        String cookies = jwcLogin.getLoginCookie(username, PasswordEncoder.encodePassword(password), option);

        System.out.println(cookies);
    }
}
