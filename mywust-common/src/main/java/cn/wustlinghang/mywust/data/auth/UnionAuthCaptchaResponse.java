package cn.wustlinghang.mywust.data.auth;

import lombok.Data;

@Data
public class UnionAuthCaptchaResponse {
    private String uid;

    private String content;

    private Integer timeout;
}