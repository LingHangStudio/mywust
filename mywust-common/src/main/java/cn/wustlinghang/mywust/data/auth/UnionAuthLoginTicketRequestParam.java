package cn.wustlinghang.mywust.data.auth;

import lombok.Data;

@Data
public class UnionAuthLoginTicketRequestParam {
    private String username;

    private String password;

    private String service;

    private String id;

    private String code;
}
