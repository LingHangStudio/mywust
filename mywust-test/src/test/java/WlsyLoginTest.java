import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.physics.PhysicsCoursePageParser;
import cn.linghang.mywust.core.service.auth.PhysicsLogin;
import cn.linghang.mywust.core.service.physics.PhysicsApiService;
import cn.linghang.mywust.model.global.Course;
import cn.linghang.mywust.model.physics.PhysicsCourse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class WlsyLoginTest {
    @Test
    public void run() throws BasicException, IOException {
        System.out.println("物理实验登陆测试");
        System.out.println("输入账号（学号）和密码，用“ ”（空格）分割");

        Scanner scanner = new Scanner(System.in);

        String input = scanner.nextLine();

        String username = input.split(" ")[0];
        String password = input.split(" ")[1];

        System.out.println("账号：" + username);
        System.out.println("密码：" + password);

        Requester requester = new SimpleOkhttpRequester();
        PhysicsLogin physicsLogin = new PhysicsLogin(requester);

        RequestClientOption option = RequestClientOption.DEFAULT_OPTION;
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setAddress("127.0.0.1");
        proxy.setPort(8080);
        option.setProxy(proxy);

        String cookies = physicsLogin.getLoginCookie(username, password, option);

        System.out.printf("获取到的cookies: %s \n", cookies);

        option.setFallowUrlRedirect(false);
        PhysicsApiService api = new PhysicsApiService(requester);
        String response = api.getCoursePage(cookies, option);
        System.out.println(response);

        Parser<List<PhysicsCourse>> parser = new PhysicsCoursePageParser();
        List<PhysicsCourse> courses = parser.parse(response);

        for (Course info : courses) {
            System.out.println(info);
        }
    }
}
