package cn.wustlinghang.mywust.captcha;

import lombok.Getter;
import lombok.Setter;

/**
 * 已处理的图片验证码，必须要和特定的未处理验证码绑定
 */
@Getter
public class SolvedImageCaptcha<T> {
    /**
     * 和验证码绑定的信息，如cookie，某种id等
     */
    private final String bindInfo;

    /**
     * 验证码验证数据
     */
    @Setter
    private String result;

    public SolvedImageCaptcha(String bindInfo) {
        this.bindInfo = bindInfo;
    }

    public SolvedImageCaptcha(UnsolvedImageCaptcha<T> unsolvedImageCaptcha) {
        this(unsolvedImageCaptcha.getBindInfo());
    }

    public SolvedImageCaptcha(UnsolvedImageCaptcha<T> unsolvedImageCaptcha, String result) {
        this(unsolvedImageCaptcha);
        this.result = result;
    }
}
