import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.undergraduate.CourseTableApi;
import cn.linghang.mywust.model.global.Course;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class CourseTableTest {
    public static void main(String[] args) throws BasicException, IOException {
        new CourseTableTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("课表");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        System.out.println("学期（如2022-2023-1）：");
        String term = scanner.nextLine();
        System.out.println("使用学期：" + term);

        Requester requester = new SimpleOkhttpRequester();
        CourseTableApi service = new CourseTableApi(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setPort(6060);
        proxy.setAddress("127.0.0.1");
        option.setProxy(proxy);
        option.setFallowUrlRedirect(false);

        List<Course> courses = service.getCourseTable(term, cookie, option);

        for (Course info : courses) {
            System.out.println(info);
        }
    }
}
