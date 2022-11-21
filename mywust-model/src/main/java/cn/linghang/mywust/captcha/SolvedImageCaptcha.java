package cn.linghang.mywust.captcha;

import lombok.Data;

/**
 * 已处理的图片验证码
 */
@Data
public class SolvedImageCaptcha {
    /**
     * 和验证码绑定的信息，如cookie，某种id等
     */
    private String bindInfo;

    /**
     * 验证码验证数据
     */
    private String result;

    public SolvedImageCaptcha(UnsolvedImageCaptcha unsolvedImageCaptcha) {
        this.bindInfo = unsolvedImageCaptcha.getBindInfo();
    }

    private void setBindInfo(String bindInfo) {}
}
