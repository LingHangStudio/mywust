package cn.linghang.mywust.network;

import lombok.Data;

@Data
public class RequestClientOption {
    private Proxy proxy;
    private long timeout;
    private boolean fallowUrlRedirect = false;

    @Data
    public static class Proxy {
        private String address;
        private int port;
    }

    public static final RequestClientOption DEFAULT_OPTION = newDefaultOption();

    private static RequestClientOption newDefaultOption() {
        RequestClientOption option = new RequestClientOption();
        option.setTimeout(5);
        option.setProxy(null);
        option.setFallowUrlRedirect(false);

        return option;
    }
}
