import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.parser.Parser;
import cn.linghang.mywust.core.parser.undergraduate.ExamInfoParser;
import cn.linghang.mywust.core.service.undergraduate.ExamInfoApiService;
import cn.linghang.mywust.model.global.ExamInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExamInfoTest {
    @Test
    public void run() throws BasicException, IOException {
        System.out.println("成绩获取");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        Requester requester = new SimpleOkhttpRequester();
        ExamInfoApiService jwcService = new ExamInfoApiService(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setPort(8080);
        proxy.setAddress("127.0.0.1");
        option.setProxy(proxy);
        option.setFallowUrlRedirect(false);

        Parser<List<ExamInfo>> parser = new ExamInfoParser();
        List<ExamInfo> infos = parser.parse(jwcService.getExamInfoPage(cookie, option));

        for (ExamInfo info : infos) {

            System.out.println(info);
        }
    }
}
