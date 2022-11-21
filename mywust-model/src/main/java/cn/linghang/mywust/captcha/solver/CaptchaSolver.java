package cn.linghang.mywust.captcha.solver;

import cn.linghang.mywust.captcha.SolvedImageCaptcha;
import cn.linghang.mywust.captcha.UnsolvedImageCaptcha;

public interface CaptchaSolver {
    SolvedImageCaptcha solve(UnsolvedImageCaptcha unsolvedImageCaptcha);
}
