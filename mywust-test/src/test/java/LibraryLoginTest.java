import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.core.service.auth.LibraryLogin;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

public class LibraryLoginTest {
    @Test
    public void run() throws BasicException, IOException {
        System.out.println("图书馆登陆测试");
        System.out.println("输入账号（学号）和密码，用“ ”（空格）分割");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String username = input.split(" ")[0];
        String password = input.split(" ")[1];

        System.out.println("账号：" + username);
        System.out.println("密码：" + password);

        Requester requester = new SimpleOkhttpRequester();
        LibraryLogin libraryLogin = new LibraryLogin(requester);

        RequestClientOption option = RequestClientOption.DEFAULT_OPTION;

        String cookies = libraryLogin.getLibraryLoginCookie(username, password, option);

        System.out.printf("获取到的cookies: %s \n", cookies);

        System.out.printf("检查Cookies: %s", libraryLogin.checkCookie(cookies));
    }
}
