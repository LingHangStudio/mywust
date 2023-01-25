import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.physics.PhysicsCoursePageParser;
import cn.linghang.mywust.core.parser.undergraduate.UndergradCreditStatusParser;
import cn.linghang.mywust.core.parser.undergraduate.UndergradExamDelayParser;
import cn.linghang.mywust.core.service.auth.PhysicsLogin;
import cn.linghang.mywust.core.service.auth.UndergraduateLogin;
import cn.linghang.mywust.core.service.physics.PhysicsApiService;
import cn.linghang.mywust.core.service.undergraduate.UndergradCreditStatusApiService;
import cn.linghang.mywust.core.service.undergraduate.UndergradExamDelayApiService;
import cn.linghang.mywust.model.global.Course;
import cn.linghang.mywust.model.physics.PhysicsCourse;
import cn.linghang.mywust.model.undergrad.ExamDelayApplication;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ParseTest {
    public static void main(String[] args) throws IOException, ParseException, ApiException {
//        Scanner scanner = new Scanner(System.in);
//
//        String cookie = scanner.nextLine();
//
//        System.out.println("Cookie：" + cookie);
//
//        Requester requester = new SimpleOkhttpRequester();
//        UndergradExamDelayApiService service = new UndergradExamDelayApiService(requester);
//
//        RequestClientOption option = new RequestClientOption();
//        RequestClientOption.Proxy proxy = RequestClientOption.Proxy.builder().build();
//        proxy.setAddress("127.0.0.1");
//        proxy.setPort(8080);
//        option.setProxy(proxy);
//
//        String data = service.getPage("2022-2023-1", "9FF84C58B1CA4BDBB656E4C224B617A9", cookie, option);
//        System.out.println(data);

        String html = Jsoup.connect("http://localhost/xf.html").get().toString();

        UndergradCreditStatusParser parser = new UndergradCreditStatusParser();
        String result = parser.parse(html);

        System.out.println(result);
    }
}
