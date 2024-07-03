package cn.wustlinghang.mywust.core.request.service.auth;

import cn.hutool.core.util.RandomUtil;
import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.core.request.factory.auth.UnionAuthRequestFactory;
import cn.wustlinghang.mywust.core.request.service.captcha.solver.CaptchaSolver;
import cn.wustlinghang.mywust.data.auth.UnionAuthCaptchaResponse;
import cn.wustlinghang.mywust.data.auth.UnionAuthLoginTicketRequestParam;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.util.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 统一认证登录
 */
public class UnionLogin {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    Logger log = LoggerFactory.getLogger(UnionLogin.class);

    private final Requester requester;

    private final CaptchaSolver<String> captchaSolver;

    public UnionLogin(Requester requester, CaptchaSolver<String> captchaSolver) {
        this.requester = requester;
        this.captchaSolver = captchaSolver;
    }

    public String getServiceTicket(String username, String password, String serviceUrl,
                                   RequestClientOption requestOption
    ) throws IOException, ApiException {
        return this.getServiceTicket(username, password, serviceUrl, 5, requestOption);
    }

    public String getServiceTicket(String username, String password, String serviceUrl, int maxRetry,
                                   RequestClientOption requestOption
    ) throws IOException, ApiException {
        String encodedPassword = PasswordEncoder.encodePassword(password);

        String captchaId = Hex.encodeHexString(RandomUtil.randomBytes(16));
        HttpRequest captchaRequest = UnionAuthRequestFactory.loginCaptchaRequest(captchaId);
        HttpResponse captchaResponse = requester.get(captchaRequest, requestOption);

        String json = captchaResponse.getStringBody();
        UnionAuthCaptchaResponse unionAuthCaptchaResponse = objectMapper.readValue(json, UnionAuthCaptchaResponse.class);

        UnsolvedImageCaptcha<String> unsolvedImageCaptcha = new UnsolvedImageCaptcha<>();
        unsolvedImageCaptcha.setBindInfo(unionAuthCaptchaResponse.getUid());
        unsolvedImageCaptcha.setImage(unionAuthCaptchaResponse.getContent());

        try {
            // 处理验证码
            SolvedImageCaptcha<String> solvedImageCaptcha = captchaSolver.solve(unsolvedImageCaptcha);

            // 获取ticket，以便在后续的系统中登录获取cookie
            UnionAuthLoginTicketRequestParam unionAuthLoginTicketRequestParam = new UnionAuthLoginTicketRequestParam();

            unionAuthLoginTicketRequestParam.setUsername(username);
            unionAuthLoginTicketRequestParam.setPassword(encodedPassword);
            unionAuthLoginTicketRequestParam.setService(serviceUrl);
            unionAuthLoginTicketRequestParam.setId(solvedImageCaptcha.getBindInfo());
            unionAuthLoginTicketRequestParam.setCode(solvedImageCaptcha.getResult());

            HttpRequest ticketRequest = UnionAuthRequestFactory.loginTicketRequest(unionAuthLoginTicketRequestParam);
            HttpResponse ticketResponse = requester.post(ticketRequest, requestOption);

            String ticketResponseBody = ticketResponse.getStringBody();
            String ticket = objectMapper.readTree(ticketResponseBody).path("ticket").asText(null);
            if (ticket == null) {
                throw  this.analyzeFailReason(ticketResponseBody);
            }

            return  ticket;
        } catch (ApiException e) {
            if (e.getCode() == ApiException.Code.CAPTCHA_WRONG && maxRetry > 0) {
                return this.getServiceTicket(username, password, serviceUrl, maxRetry - 1, requestOption);
            } else {
                throw e;
            }
        }
    }

    private ApiException analyzeFailReason(String response) {
        try {
            String code = objectMapper.readTree(response)
                    .path("data").path("code")
                    .asText("");
            switch (code) {
                case "PASSERROR":
                case "FALSE":
                    return new ApiException(ApiException.Code.UNI_LOGIN_PASSWORD_WRONG);
                case "NOUSER":
                    return new ApiException(ApiException.Code.UNI_LOGIN_USER_NOT_EXISTS);
                case "USERLOCK":
                    return new ApiException(ApiException.Code.UNI_LOGIN_USER_BANNED);
                case "USERDISABLED":
                    return new ApiException(ApiException.Code.UNI_LOGIN_USER_DISABLED);
                case "ISMODIFYPASS":
                    return new ApiException(ApiException.Code.UNI_LOGIN_NEED_CHANGE_PASSWORD);
                case "USERNOTONLY":
                    return new ApiException(ApiException.Code.UNI_LOGIN_USER_NOT_ONLY);
                case "NOREGISTER":
                    return new ApiException(ApiException.Code.UNI_LOGIN_NO_REGISTER);
                case "TWOVERIFY":
                    return new ApiException(ApiException.Code.UNI_LOGIN_NEED_TFA);
                case "CODEFALSE":
                    return new ApiException(ApiException.Code.CAPTCHA_WRONG);
                default:
                    log.warn("未知的原因：{}", code);
                    return new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, "未知的错误原因：" + code);
            }
        } catch (Exception e) {
            log.warn("分析失败原因出错：{}， 响应：{}", e, response);
            return new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, e.toString());
        }
    }
}
