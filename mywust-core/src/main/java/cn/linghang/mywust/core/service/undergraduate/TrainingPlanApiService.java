package cn.linghang.mywust.core.service.undergraduate;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.undergrade.BkjxRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

public class TrainingPlanApiService extends UndergraduateApiService {

    public TrainingPlanApiService(Requester requester) {
        super(requester);
    }

    public String getTrainingPlanPage(String cookies, RequestClientOption requestClientOption) throws IOException, ApiException {
        HttpRequest request = BkjxRequestFactory.trainingPlanPageRequest(cookies);
        HttpResponse response = requester.get(request, requestClientOption);

        super.checkResponse(response);

        return new String(response.getBody());
    }
}
