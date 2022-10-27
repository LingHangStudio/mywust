package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.CookieInvalidException;
import cn.linghang.mywust.core.exception.ParseException;
import cn.linghang.mywust.core.parser.undergraduate.TrainingPlanPageParser;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class TrainingPlanApi extends UndergraduateApi {
    private final Requester requester;

    private static final TrainingPlanPageParser parser = new TrainingPlanPageParser();

    public TrainingPlanApi(Requester requester) {
        this.requester = requester;
    }

    public String getTrainingPlanPage(String cookies, RequestClientOption requestClientOption) throws CookieInvalidException, IOException {
        HttpRequest request = BkjxRequestFactory.trainingPlanPageRequest(cookies);
        HttpResponse response = requester.get(request, requestClientOption);

        checkResponse(response, cookies);

        return new String(response.getBody());
    }

    public String getPrueSchemePage(String cookies, RequestClientOption requestClientOption) throws IOException, CookieInvalidException, ParseException {
        String fullPage = this.getTrainingPlanPage(cookies, requestClientOption);
        return parser.parse(fullPage);
    }
}
