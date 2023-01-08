package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.request.auth.UnionAuthRequestFactory;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.util.PasswordEncoder;
import com.fasterxml.jackson.databind.JsonNode;
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
            throw new ApiException(this.analyzeFailReason(unionAuthResponse.getBody()));
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

    private ApiException.Code analyzeFailReason(byte[] response) {
        try {
            JsonNode dataNode = new ObjectMapper().readTree(response).get("data");
            switch (dataNode.get("code").asText()) {
                case "PASSERROR":
                    return ApiException.Code.UNI_LOGIN_PASSWORD_WRONG;
                case "NOUSER":
                    return ApiException.Code.UNI_LOGIN_USER_NOT_EXISTS;
                case "USERLOCK":
                    return ApiException.Code.UNI_LOGIN_USER_BANNED;
                default:
                    return ApiException.Code.UNKNOWN_EXCEPTION;
            }
        } catch (Exception e) {
            return ApiException.Code.UNKNOWN_EXCEPTION;
        }
    }
}
