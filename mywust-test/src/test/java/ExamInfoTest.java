import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.undergraduate.ExamInfoApi;
import cn.linghang.mywust.model.undergrade.ExamInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class ExamInfoTest {
    public static void main(String[] args) throws BasicException, IOException {
        new ExamInfoTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("成绩获取");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        Requester requester = new SimpleOkhttpRequester();
        ExamInfoApi jwcService = new ExamInfoApi(requester);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setPort(6060);
        proxy.setAddress("127.0.0.1");
        option.setProxy(proxy);
        option.setFallowUrlRedirect(false);

        List<ExamInfo> infos = jwcService.getExamInfo(cookie, option);

        for (ExamInfo info : infos) {

            System.out.println(info);
        }
    }
}
