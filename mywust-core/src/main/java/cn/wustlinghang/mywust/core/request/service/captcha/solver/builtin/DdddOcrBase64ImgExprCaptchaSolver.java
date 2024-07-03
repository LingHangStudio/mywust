package cn.wustlinghang.mywust.core.request.service.captcha.solver.builtin;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.core.request.service.captcha.solver.CaptchaSolver;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;

import java.io.IOException;

public class DdddOcrBase64ImgExprCaptchaSolver implements CaptchaSolver<String> {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    private final String baseUrl;

    private final Requester requester;

    public DdddOcrBase64ImgExprCaptchaSolver(String baseUrl, Requester requester) {
        this.baseUrl = baseUrl;
        this.requester = requester;
    }

    @Override
    public SolvedImageCaptcha<String> solve(UnsolvedImageCaptcha<String> unsolvedImageCaptcha) throws ApiException {
        try {
            SolvedImageCaptcha<String> solvedImageCaptcha = new SolvedImageCaptcha<>(unsolvedImageCaptcha);

            String result = this.ocr(unsolvedImageCaptcha.getImage());
            solvedImageCaptcha.setResult(result);
            return solvedImageCaptcha;
        } catch (IOException e) {
            throw new ApiException(ApiException.Code.NETWORK_EXCEPTION);
        }
    }

    @Data
    private static class OcrResponse {
        private int code;
        private String err;
        private String result;
    }

    private String ocr(String base64URIData) throws IOException, ApiException {
        HttpRequest request = RequestFactory.makeHttpRequest(baseUrl + "/solve", base64URIData);
        HttpResponse response = requester.post(request);
        OcrResponse ocrResponse = objectMapper.readValue(response.getBody(), OcrResponse.class);
        if (ocrResponse.code != 0) {
            throw new ApiException(ApiException.Code.CAPTCHA_WRONG);
        }

        return ocrResponse.getResult();
    }
}
