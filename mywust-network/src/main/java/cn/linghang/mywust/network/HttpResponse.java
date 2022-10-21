package cn.linghang.mywust.network;

import lombok.Data;

import java.util.Map;

@Data
public class HttpResponse {
    private Map<String, String> headers;

    private String cookies;

    private byte[] body;

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("HttpResponse{");
        sb.append("headers=").append(headers);
        sb.append(", cookies='").append(cookies).append('\'');
        sb.append(", body=");
        if (body == null) sb.append("null");
        else {
            sb.append(new String(body));
        }
        sb.append('}');
        return sb.toString();
    }
}
