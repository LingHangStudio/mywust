package cn.wustlinghang.mywust.core.request.service.captcha.solver;

import cn.wustlinghang.mywust.captcha.SolvedImageCaptcha;
import cn.wustlinghang.mywust.captcha.UnsolvedImageCaptcha;
import cn.wustlinghang.mywust.exception.ApiException;

public interface CaptchaSolver<T> {
    SolvedImageCaptcha<T> solve(UnsolvedImageCaptcha<T> unsolvedImageCaptcha) throws ApiException;
}
