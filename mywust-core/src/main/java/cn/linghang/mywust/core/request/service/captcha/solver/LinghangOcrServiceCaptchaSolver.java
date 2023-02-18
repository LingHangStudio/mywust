package cn.linghang.mywust.core.request.service.captcha.solver;

import cn.linghang.mywust.captcha.SolvedImageCaptcha;
import cn.linghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.factory.RequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.util.StringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Base64;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class LinghangOcrServiceCaptchaSolver implements CaptchaSolver {
    private final String baseUrl;
    private final Requester requester;

    private final String appId;
    private final String secretKey;

    private static final RequestClientOption requestOption = new RequestClientOption();

    public LinghangOcrServiceCaptchaSolver(String baseUrl, Requester requester,
                                           String appId, String secretKey) {

        this.baseUrl = baseUrl;
        this.requester = requester;

        this.appId = appId;
        this.secretKey = secretKey;
    }

    @Override
    public SolvedImageCaptcha solve(UnsolvedImageCaptcha unsolvedImageCaptcha) throws ApiException {
        Map<String, String> urlParams = new TreeMap<>(String::compareTo);
        urlParams.put("id", appId);
        urlParams.put("t", Long.toString(System.currentTimeMillis() / 1000));
        urlParams.put("type", "json");

        String sign = StringUtil.generateSignText(appId, secretKey, urlParams);
        urlParams.put("sign", sign);

        String url = baseUrl + StringUtil.generateQueryString(urlParams);
        byte[] imageBase64Data = Base64.encodeBase64(unsolvedImageCaptcha.getImage());

        HttpRequest request;
        HttpResponse response;
        OcrResult result;
        try {
            request = RequestFactory.makeHttpRequest(url, imageBase64Data);
            response = requester.post(request, requestOption);
            result = new ObjectMapper().readValue(response.getBody(), OcrResult.class);
        } catch (IOException e) {
            throw new ApiException(ApiException.Code.NETWORK_EXCEPTION);
        }

        if (result.code != OcrResult.CODE_SUCCESS) {
            throw new ApiException(ApiException.Code.INTERNAL_EXCEPTION);
        }

        return new SolvedImageCaptcha(unsolvedImageCaptcha, result.result);
    }

    private static class OcrResult {
        private int code;
        private String result;
        private String message;

        public static final int CODE_SUCCESS = 0;
    }

}
