package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.ExamInfoParser;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.model.global.ExamInfo;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class ExamInfoApiService extends UndergraduateApiService {

    public ExamInfoApiService(Requester requester) {
        super(requester);
    }

    public String getExamInfoPage(String cookies, RequestClientOption requestClientOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.examScoreInfoRequest(cookies, "", "", "");
        HttpResponse response = requester.post(request, requestClientOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }
}
