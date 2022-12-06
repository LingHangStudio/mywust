import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.service.undergraduate.TrainingPlanApiService;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.okhttp.SimpleOkhttpRequester;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Scanner;

public class SchemeTest {
    @Test
    public void run() throws BasicException, IOException {
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
        TrainingPlanApiService jwcService = new TrainingPlanApiService(requester);

        String page = jwcService.getTrainingPlanPage(cookie, option);

        System.out.println(page);
    }
}
