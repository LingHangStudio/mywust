package cn.linghang.mywust.network;

import lombok.Data;

@Data
public class RequestClientOption {
    private Proxy proxy;
    private long timeout;

    @Data
    public static class Proxy {
        private String address;
        private int port;
    }
}
