package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.ExamInfoParser;
import cn.linghang.mywust.core.request.BkjxRequestFactory;
import cn.linghang.mywust.core.util.BkjxUtil;
import cn.linghang.mywust.model.undergrade.ExamInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ExamInfoApi {
    private static final Logger log = LoggerFactory.getLogger(ExamInfoApi.class);

    private final Requester requester;

    private static final ExamInfoParser parser = new ExamInfoParser();

    public ExamInfoApi(Requester requester) {
        this.requester = requester;
    }

    public String getExamInfoPage(String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException {
        HttpRequest request = BkjxRequestFactory.examScoreInfoRequest(cookies, "", "", "");
        HttpResponse response = requester.post(request, requestClientOption);

        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                BkjxUtil.checkLoginFinger(response.getBody())) {

            throw new CookieInvalidException("[请求获取成绩]：Cookie无效：" + cookies);
        }

        return new String(response.getBody());
    }

    public List<ExamInfo> getExamInfo(String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException, ParseException {
        String examInfoPage = this.getExamInfoPage(cookies, requestClientOption);

        return parser.parse(examInfoPage);
    }
}
