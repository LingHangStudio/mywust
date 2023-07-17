package cn.wustlinghang.mywust.core.request.service.captcha.solver;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;
import java.util.Base64;

public class DdddOcrCaptchaSolver implements CaptchaSolver {
    private final String baseUrl;

    private final Requester requester;

    public DdddOcrCaptchaSolver(String baseUrl, Requester requester) {
        this.baseUrl = baseUrl;
        this.requester = requester;
    }

    @Override
    public SolvedImageCaptcha solve(UnsolvedImageCaptcha unsolvedImageCaptcha) throws ApiException {
        try {
            SolvedImageCaptcha solvedImageCaptcha = new SolvedImageCaptcha(unsolvedImageCaptcha);

            String result = this.ocr(unsolvedImageCaptcha.getImage());
            solvedImageCaptcha.setResult(result);
            return solvedImageCaptcha;
        } catch (IOException e) {
            throw new ApiException(ApiException.Code.NETWORK_EXCEPTION);
        }
    }

    private String ocr(byte[] data) throws IOException {
        HttpRequest request = RequestFactory.makeHttpRequest(baseUrl + "/ocr/b64/text", Base64.getEncoder().encode(data));
        HttpResponse response = requester.post(request);

        return response.getStringBody();
    }
}
