import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.undergraduate.TrainingPlanApi;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;

import java.io.IOException;
import java.util.Scanner;

public class SchemeTest {
    public static void main(String[] args) throws BasicException, IOException {
        new SchemeTest().run();
    }

    private void run() throws BasicException, IOException {
        System.out.println("培养方案获取");
        System.out.println("Cookie：");

        Scanner scanner = new Scanner(System.in);

        String cookie = scanner.nextLine();

        System.out.println("使用Cookie：" + cookie);

        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        RequestClientOption.Proxy proxy = new RequestClientOption.Proxy();
        proxy.setPort(6060);
        proxy.setAddress("127.0.0.1");
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        Requester requester = new SimpleOkhttpRequester();
        TrainingPlanApi jwcService = new TrainingPlanApi(requester);

        String page = jwcService.getPrueSchemePage(cookie, option);

        System.out.println(page);
    }
}
