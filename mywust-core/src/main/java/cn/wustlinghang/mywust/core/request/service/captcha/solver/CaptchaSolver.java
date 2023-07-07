package cn.wustlinghang.mywust.core.request.service.captcha.solver;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.exception.ApiException;

public interface CaptchaSolver {
    SolvedImageCaptcha solve(UnsolvedImageCaptcha unsolvedImageCaptcha) throws ApiException;
}
