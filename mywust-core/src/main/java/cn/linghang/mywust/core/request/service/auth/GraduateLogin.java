package cn.linghang.mywust.core.request.service.auth;

import cn.linghang.mywust.captcha.SolvedImageCaptcha;
import cn.linghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.linghang.mywust.core.api.GraduateUrls;
import cn.linghang.mywust.core.exception.ApiException;
import cn.linghang.mywust.core.request.factory.RequestFactory;
import cn.linghang.mywust.core.request.factory.graduate.GraduateRequestFactory;
import cn.linghang.mywust.core.request.service.captcha.solver.CaptchaSolver;
import cn.linghang.mywust.network.RequestClientOption;
import cn.linghang.mywust.network.Requester;
import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class GraduateLogin {
    private final Requester requester;

    private final CaptchaSolver captchaSolver;

    public GraduateLogin(Requester requester, CaptchaSolver captchaSolver) {
        this.requester = requester;
        this.captchaSolver = captchaSolver;
    }

    public String getLoginCookie(String username, String password, RequestClientOption option) throws IOException, ApiException {
        // 请求获取验证码
        HttpRequest captchaImageRequest = GraduateRequestFactory.captchaRequest();
        HttpResponse captchaImageResponse = requester.get(captchaImageRequest, option);

        UnsolvedImageCaptcha unsolvedImageCaptcha = new UnsolvedImageCaptcha();
        unsolvedImageCaptcha.setBindInfo(captchaImageResponse.getCookies());

        byte[] processedImage = ImageUtil.process(captchaImageResponse.getBody());
        unsolvedImageCaptcha.setImage(processedImage);

        // 通过传入的captchaSolver来处理验证码
        SolvedImageCaptcha solvedImageCaptcha = captchaSolver.solve(unsolvedImageCaptcha);

        // 进行登录
        HttpRequest loginRequest = GraduateRequestFactory.loginRequest(username, password, solvedImageCaptcha);
        HttpResponse loginResponse = requester.post(loginRequest, option);

        // 登陆成功，应该会是302跳转，不是的话多半是认证错误
        if (loginResponse.getStatusCode() != HttpResponse.HTTP_REDIRECT_302) {
            throw new ApiException(ApiException.Code.GRADUATE_PASSWORD_WRONG);
        }

        // 使用当初通过验证码得到的cookie来作为登录cookie，至于是否真正可行待验证
        return captchaImageResponse.getCookies();
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