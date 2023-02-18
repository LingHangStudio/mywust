package cn.linghang.mywust.core.request.service.captcha.solver;

import cn.linghang.mywust.captcha.SolvedImageCaptcha;
import cn.linghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.linghang.mywust.core.exception.ApiException;

public interface CaptchaSolver {
    SolvedImageCaptcha solve(UnsolvedImageCaptcha unsolvedImageCaptcha) throws ApiException;
}
