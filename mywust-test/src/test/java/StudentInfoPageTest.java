import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.StudentInfoPageParser;
import cn.linghang.mywust.core.service.undergraduate.StudentInfoApiService;
import cn.linghang.mywust.model.global.StudentInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

public class StudentInfoPageTest {
    @Test
    public void run() throws BasicException, IOException {
        System.out.println("学生信息获取");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        Requester requester = new SimpleOkhttpRequester();
        StudentInfoApiService jwcService = new StudentInfoApiService(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        Parser<StudentInfo> parser = new StudentInfoPageParser();
        StudentInfo info = parser.parse(jwcService.getStudentInfoPage(cookie, option));

        System.out.println(info);
    }
}
