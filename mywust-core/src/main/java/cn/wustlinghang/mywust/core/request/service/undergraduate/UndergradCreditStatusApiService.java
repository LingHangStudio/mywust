package cn.wustlinghang.mywust.core.request.service.undergraduate;

import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.exception.ParseException;
import cn.wustlinghang.mywust.core.parser.undergraduate.UndergradCreditStatusIndexParser;
import cn.wustlinghang.mywust.core.request.factory.undergrade.BkjxRequestFactory;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Map;

public class UndergradCreditStatusApiService extends UndergradApiServiceBase {
    private static final UndergradCreditStatusIndexParser parser = new UndergradCreditStatusIndexParser();

    public UndergradCreditStatusApiService(Requester requester) {
        super(requester);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params, RequestClientOption option) throws ApiException, IOException {
        return this.getPage(cookie, option, false);
    }

    @Override
    public String getPage(String cookie, Map<String, String> params) throws ApiException, IOException {
        return this.getPage(cookie, params, null);
    }

    public String getPage(String cookie, RequestClientOption option, boolean quick) throws IOException, ApiException {
        HttpRequest request;
        if (quick) {
            request = BkjxRequestFactory.creditStatusPageRequest(cookie);
        } else {
            HttpRequest indexRequest = BkjxRequestFactory.creditStatusIndexPageRequest(cookie);
            HttpResponse indexResponse = requester.get(indexRequest, option);
            this.checkResponse(indexResponse);

            String indexHtml = indexResponse.getStringBody();
            Map<String, String> params;
            try {
                params = parser.parse(indexHtml);
            } catch (ParseException e) {
                throw new ApiException(ApiException.Code.COOKIE_INVALID);
            }

            request = BkjxRequestFactory.creditStatusPageRequest(cookie, params);
        }

        HttpResponse response = requester.post(request, option);
        this.checkResponse(response);

        return response.getStringBody();
    }
}
