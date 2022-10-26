package cn.linghang.mywust.core.service.auth;

import cn.linghang.mywust.core.exception.BasicException;
import cn.linghang.mywust.core.exception.PasswordWornException;
import cn.linghang.mywust.core.request.AuthRequestFactory;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.util.PasswordEncoder;
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

    public String getServiceTicket(String username, String password, String serviceUrl, RequestClientOption requestOption) throws IOException, BasicException {
        String encodedPassword = PasswordEncoder.encodePassword(password);

        // 获取ticket granting ticket（TGT），以获取ServiceTicket
        HttpRequest TGTRequest = AuthRequestFactory.unionLoginTGTRequest(username, encodedPassword, serviceUrl);
        HttpResponse unionAuthResponse = requester.post(TGTRequest, requestOption);

        String redirectAddress = unionAuthResponse.getHeaders().get("Location");
        if (redirectAddress == null) {
            throw new PasswordWornException();
        }

        // 获取服务ticket（service ticket，ST）
        HttpRequest serviceTicketRequest = AuthRequestFactory.loginTicketRequest(redirectAddress.replace("http:", "https:"), serviceUrl);
        HttpResponse serviceTicketResponse = requester.post(serviceTicketRequest, requestOption);

        byte[] serviceTicketResponseData = serviceTicketResponse.getBody();
        if (serviceTicketResponseData == null) {
            log.warn("获取服务st出错，serviceTicketResponseData == null");

            throw new BasicException();
        }

        return new String(serviceTicketResponseData);
    }
}
