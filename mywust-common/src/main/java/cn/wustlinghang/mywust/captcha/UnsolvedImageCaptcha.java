package cn.wustlinghang.mywust.captcha;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 待处理的图片验证码
 */
@Data
public class UnsolvedImageCaptcha<T> {
    /**
     * 和验证码绑定的信息，如cookie，某种id等
     */
    private String bindInfo;

    /**
     * 验证码图片，使用byte数组存储
     */
    private T image;
}
