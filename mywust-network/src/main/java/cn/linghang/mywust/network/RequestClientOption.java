package cn.linghang.mywust.network;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestClientOption {
    private Proxy proxy;
    private long timeout;
    private boolean fallowUrlRedirect;

    private boolean ignoreSSLError;

    @Data
    @Builder
    public static class Proxy {
        private String address;
        private int port;
    }

    public RequestClientOption() {
        this.proxy = null;
        this.timeout = 5;
        this.fallowUrlRedirect = false;
        this.ignoreSSLError = true;
    }

    public RequestClientOption(Proxy proxy, long timeout, boolean fallowUrlRedirect, boolean ignoreSSLError) {
        this.proxy = proxy;
        this.timeout = timeout;
        this.fallowUrlRedirect = fallowUrlRedirect;
        this.ignoreSSLError = ignoreSSLError;
    }

    public static final RequestClientOption DEFAULT_OPTION = newDefaultOption();

    private static RequestClientOption newDefaultOption() {
        return new ConstantRequestClientOption();
    }

    /**
     * 常量化的RequestClientOption，对其使用setter时不会起任何作用
     */
    public static class ConstantRequestClientOption extends RequestClientOption {
        @Override
        public Proxy getProxy() {
            return super.getProxy();
        }

        @Override
        public long getTimeout() {
            return super.getTimeout();
        }

        @Override
        public boolean isFallowUrlRedirect() {
            return super.isFallowUrlRedirect();
        }

        @Override
        public void setProxy(Proxy proxy) {}

        @Override
        public void setTimeout(long timeout) {}

        @Override
        public void setFallowUrlRedirect(boolean fallowUrlRedirect) {}
    }

    public static RequestClientOptionBuilder builder() {
        return new RequestClientOptionBuilder();
    }

    public static final class RequestClientOptionBuilder {
        private final RequestClientOption requestClientOption;

        private RequestClientOptionBuilder() {
            requestClientOption = new RequestClientOption();
        }

        public RequestClientOptionBuilder proxy(Proxy proxy) {
            requestClientOption.setProxy(proxy);
            return this;
        }

        public RequestClientOptionBuilder timeout(long timeout) {
            requestClientOption.setTimeout(timeout);
            return this;
        }

        public RequestClientOptionBuilder fallowUrlRedirect(boolean fallowUrlRedirect) {
            requestClientOption.setFallowUrlRedirect(fallowUrlRedirect);
            return this;
        }

        public RequestClientOptionBuilder ignoreSSLError(boolean ignoreSSLError) {
            requestClientOption.setIgnoreSSLError(ignoreSSLError);
            return this;
        }

        public RequestClientOption build() {
            return requestClientOption;
        }
    }
}
