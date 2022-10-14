package cn.linghang.mywust.network;

import lombok.Data;

import java.util.Map;

@Data
public class HttpRequest {
    private Map<String, String> urlParams;

    private Map<String, String> headers;

    private String cookies;

    private byte[] data;
}
