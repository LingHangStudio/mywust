package cn.wustlinghang.mywust.core.request.service.auth;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.core.request.factory.graduate.GraduateRequestFactory;
import cn.wustlinghang.mywust.core.request.service.captcha.solver.CaptchaSolver;
import cn.wustlinghang.mywust.exception.ApiException;
import cn.wustlinghang.mywust.network.RequestClientOption;
import cn.wustlinghang.mywust.network.Requester;
import cn.wustlinghang.mywust.network.entitys.HttpRequest;
import cn.wustlinghang.mywust.network.entitys.HttpResponse;
import cn.wustlinghang.mywust.network.request.RequestFactory;
import cn.wustlinghang.mywust.urls.GraduateUrls;
import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Slf4j
public class GraduateLogin {
    private final Requester requester;

    private final CaptchaSolver<byte[]> captchaSolver;

    public GraduateLogin(Requester requester, CaptchaSolver<byte[]> captchaSolver) {
        this.requester = requester;
        this.captchaSolver = captchaSolver;
    }

    public String getLoginCookie(String username, String password, RequestClientOption option) throws IOException, ApiException {
        HttpRequest loginIndexRequest = RequestFactory.makeHttpRequest(GraduateUrls.GRADUATE_LOGIN_API);
        HttpResponse loginIndexResponse = requester.get(loginIndexRequest, option);

        String loginCookie = loginIndexResponse.getCookies();

        // 用得到的Cookie请求获取验证码
        HttpRequest captchaImageRequest = GraduateRequestFactory.captchaRequest(loginCookie);
        HttpResponse captchaImageResponse = requester.get(captchaImageRequest, option);

        UnsolvedImageCaptcha<byte[]> unsolvedImageCaptcha = new UnsolvedImageCaptcha<>();
        unsolvedImageCaptcha.setBindInfo(loginCookie);

        byte[] processedImage = ImageUtil.process(captchaImageResponse.getBody());
        unsolvedImageCaptcha.setImage(processedImage);

        // 通过传入的captchaSolver来处理验证码
        SolvedImageCaptcha<byte[]> solvedImageCaptcha = captchaSolver.solve(unsolvedImageCaptcha);

        // 进行登录
        String loginIndexHtml = loginIndexResponse.getStringBody();
        HttpRequest loginRequest = GraduateRequestFactory.loginRequest(username, password, loginIndexHtml, solvedImageCaptcha);
        HttpResponse loginResponse = requester.post(loginRequest, option);

        // 登陆成功，应该会是302跳转，不是的话多半是认证错误
        if (loginResponse.getStatusCode() != HttpResponse.HTTP_REDIRECT_302) {
            String responseHtml = loginResponse.getStringBody();
            if (responseHtml.contains("验证码错误")) {
                throw new ApiException(ApiException.Code.GRADUATE_CAPTCHA_WRONG);
            } else if (responseHtml.contains("密码错误") || responseHtml.contains("用户名不存在")) {
                throw new ApiException(ApiException.Code.GRADUATE_PASSWORD_WRONG);
            } else {
                throw new ApiException(ApiException.Code.UNKNOWN_EXCEPTION);
            }
        }

        // 使用首页第一次访问得到的cookie来作为登录cookie
        return loginCookie;
    }

    public String getLoginCookie(String username, String password, int maxRetryTimes, RequestClientOption option)
            throws IOException, ApiException {
        return getLoginCookie(username, password, maxRetryTimes, 0, option);
    }

    private String getLoginCookie(String username, String password, int maxRetryTimes, int cnt, RequestClientOption option)
            throws IOException, ApiException {
        try {
            return getLoginCookie(username, password, option);
        } catch (ApiException e) {
            boolean shouldRetry = (cnt < maxRetryTimes) && (e.getCode() != ApiException.Code.GRADUATE_CAPTCHA_WRONG);
            if (shouldRetry) {
                log.info("[mywust]: Retrying login for {} time(s)", cnt + 1);
                return getLoginCookie(username, password, maxRetryTimes, cnt + 1, option);
            } else {
                throw e;
            }
        }
    }

    public void checkCookies(String cookie, RequestClientOption option) throws ApiException, IOException {
        HttpRequest request = RequestFactory.makeHttpRequest(GraduateUrls.GRADUATE_INDEX_TEST_API, null, cookie);
        HttpResponse response = requester.get(request, option);

        this.checkResponse(response);
    }

    public void checkResponse(HttpResponse response) throws ApiException {
        // 检查响应是否正确
        if (response.getBody() == null ||
                response.getStatusCode() != HttpResponse.HTTP_OK ||
                new String(response.getBody()).contains("name=\"_ctl0:txtpassword\"")) {

            throw new ApiException(ApiException.Code.COOKIE_INVALID);
        }
    }
}

class ImageUtil {
    // 验证码图片的长宽
    private static final int IMAGE_WIDTH = 75;
    private static final int IMAGE_HEIGHT = 35;

    // 二值化阈值，实测339效果最佳
    private static final int LIMIT = 339;

    private static final int BLACK = Color.BLACK.getRGB();
    private static final int WHITE = Color.WHITE.getRGB();

    /**
     * 初步处理验证码图片，对图片去色处理以更好地进行ocr处理
     */
    public static byte[] process(byte[] data) throws ApiException {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
            BufferedImage bufferedImage = ImageIO.read(byteArrayInputStream);

            for (int i = 0; i < IMAGE_WIDTH; ++i) {
                for (int j = 0; j < IMAGE_HEIGHT; ++j) {
                    int rgb = bufferedImage.getRGB(i, j);
                    int bright = ((rgb >> 16) & 0xff) + ((rgb >> 8) & 0xff) + (rgb & 0xff);

                    bufferedImage.setRGB(i, j, bright <= LIMIT ? BLACK : WHITE);
                }
            }

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            ImageIO.write(bufferedImage, "png", outputStream);

            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new ApiException(ApiException.Code.INTERNAL_EXCEPTION);
        }
    }
}