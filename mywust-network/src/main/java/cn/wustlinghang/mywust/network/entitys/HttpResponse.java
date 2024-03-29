package cn.wustlinghang.mywust.network.entitys;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HttpResponse {
    public static final int HTTP_OK = 200;
    public static final int HTTP_RESOURCE_CREATED = 201;

    public static final int HTTP_REDIRECT_301 = 301;
    public static final int HTTP_REDIRECT_302 = 302;

    public static final int HTTP_FORBIDDEN = 403;
    public static final int HTTP_NOT_FOUND = 404;

    public static final int HTTP_SERVER_ERROR = 500;
    public static final int HTTP_SERVICE_UNAVAILABLE = 503;

    private int statusCode;

    private Map<String, String> headers;

    private String cookies;

    private byte[] body;

    public String getStringBody() {
        return new String(body, StandardCharsets.UTF_8);
    }

    public String getStringBody(Charset charsets) {
        return new String(body, charsets);
    }

    public void setBody(byte[] body) {
        this.body = body;
    }

    public void setBody(String stringBody) {
        this.body = stringBody.getBytes(StandardCharsets.UTF_8);
    }

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
