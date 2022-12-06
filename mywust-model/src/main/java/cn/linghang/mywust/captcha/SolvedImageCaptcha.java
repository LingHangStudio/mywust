package cn.linghang.mywust.captcha;

import lombok.Data;
import lombok.Getter;

/**
 * 已处理的图片验证码，必须要和特定的未处理验证码绑定
 */
@Getter
public class SolvedImageCaptcha {
    /**
     * 和验证码绑定的信息，如cookie，某种id等
     */
    private final String bindInfo;

    /**
     * 验证码验证数据
     */
    private String result;

    public SolvedImageCaptcha(String bindInfo) {
        this.bindInfo = bindInfo;
    }

    public SolvedImageCaptcha(UnsolvedImageCaptcha unsolvedImageCaptcha) {
        this(unsolvedImageCaptcha.getBindInfo());
    }

    public SolvedImageCaptcha(UnsolvedImageCaptcha unsolvedImageCaptcha, String result) {
        this(unsolvedImageCaptcha);
        this.result = result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
