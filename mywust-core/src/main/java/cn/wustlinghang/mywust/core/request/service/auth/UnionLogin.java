package cn.wustlinghang.mywust.core.request.service.auth;

import cn.wustlinghang.mywust.core.request.factory.auth.UnionAuthRequestFactory;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.util.PasswordEncoder;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public UnionLogin(Requester requester) {
        this.requester = requester;
    }

    public String getServiceTicket(String username, String password, String serviceUrl, RequestClientOption requestOption) throws IOException, ApiException {
        String encodedPassword = PasswordEncoder.encodePassword(password);

        // 获取ticket，以便在后续的系统中登录获取cookie
        HttpRequest ticketRequest = UnionAuthRequestFactory.loginTicketRequest(username, encodedPassword, serviceUrl);
        HttpResponse ticketResponse = requester.post(ticketRequest, requestOption);

        String ticketResponseBody = ticketResponse.getStringBody();
        String ticket = objectMapper.readTree(ticketResponseBody).path("ticket").asText(null);
        if (ticket == null) {
            throw analyzeFailReason(ticketResponseBody);
        }

        return ticket;
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
