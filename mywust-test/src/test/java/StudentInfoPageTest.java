import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.parser.undergraduate.StudentInfoPageParser;
import cn.linghang.mywust.core.service.auth.JwcLogin;
import cn.linghang.mywust.core.service.auth.UnionLogin;
import cn.linghang.mywust.core.service.undergraduate.StudentInfoApi;
import cn.linghang.mywust.model.undergrade.StudentInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.Scanner;

public class StudentInfoPageTest {
    public static void main(String[] args) throws BasicException, IOException {
        new StudentInfoPageTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("学生信息获取");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        Requester requester = new SimpleOkhttpRequester();
        StudentInfoApi jwcService = new StudentInfoApi(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        StudentInfo info = jwcService.getStudentInfo(cookie, option);

        System.out.println(info);
    }
}
