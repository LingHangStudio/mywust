package cn.linghang.mywust.network;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class HttpResponse {
    private Map<String, String> headers;

    private String cookies;

    private byte[] body;
}
