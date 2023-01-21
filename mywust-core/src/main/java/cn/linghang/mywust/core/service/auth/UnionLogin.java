package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.auth.UnionAuthRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.util.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * 统一认证登录
 */
public class UnionLogin {
    Logger log = LoggerFactory.getLogger(UnionLogin.class);

    private final Requester requester;

    public UnionLogin(Requester requester) {
        this.requester = requester;
    }

    public String getServiceTicket(String username, String password, String serviceUrl, RequestClientOption requestOption) throws IOException, ApiException {
        String encodedPassword = PasswordEncoder.encodePassword(password);

        // 获取ticket granting ticket（TGT），以获取ServiceTicket
        HttpRequest TGTRequest = UnionAuthRequestFactory.unionLoginTGTRequest(username, encodedPassword, serviceUrl);
        HttpResponse unionAuthResponse = requester.post(TGTRequest, requestOption);

        String redirectAddress = unionAuthResponse.getHeaders().get("Location");
        if (redirectAddress == null) {
            throw analyzeFailReason(unionAuthResponse.getBody());
        }

        // 获取服务ticket（service ticket，ST）
        HttpRequest serviceTicketRequest = UnionAuthRequestFactory.loginTicketRequest(redirectAddress.replace("http:", "https:"), serviceUrl);
        HttpResponse serviceTicketResponse = requester.post(serviceTicketRequest, requestOption);

        byte[] serviceTicketResponseData = serviceTicketResponse.getBody();
        if (serviceTicketResponseData == null) {
            log.warn("获取服务st出错，serviceTicketResponseData == null");

            throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
        }

        return new String(serviceTicketResponseData);
    }

    private ApiException analyzeFailReason(byte[] response) {
        try {
            String code = new ObjectMapper().readTree(response).get("data").get("code").asText();
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
                default:
                    log.warn("未知的原因：{}", code);
                    return new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, "未知的错误原因：" + code);
            }
        } catch (Exception e) {
            log.warn("分析失败原因出错：{}， 响应：{}", e, new String(response));
            return new ApiException(ApiException.Code.UNKNOWN_EXCEPTION, e.toString());
        }
    }
}
