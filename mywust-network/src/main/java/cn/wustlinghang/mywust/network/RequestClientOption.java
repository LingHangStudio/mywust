package cn.wustlinghang.mywust.network;

import lombok.Builder;
import lombok.Data;

@Data
public class RequestClientOption {
    private Proxy proxy;
    private long timeout;
    private boolean followUrlRedirect;

    private boolean retryable;
    private int maxRetryTimes;

    private boolean ignoreSSLError;

    @Data
    @Builder
    public static class Proxy {
        private String address;
        private int port;
    }

    public RequestClientOption() {
        this.proxy = null;
        this.timeout = 10;
        this.followUrlRedirect = false;
        this.retryable = true;
        this.maxRetryTimes = 3;
        this.ignoreSSLError = true;
    }

    public RequestClientOption(Proxy proxy, long timeout, boolean followUrlRedirect,
                               boolean retryable, int maxRetryTimes, boolean ignoreSSLError) {
        this.proxy = proxy;
        this.timeout = timeout;
        this.followUrlRedirect = followUrlRedirect;
        this.retryable = retryable;
        this.maxRetryTimes = maxRetryTimes;
        this.ignoreSSLError = ignoreSSLError;
    }

    public static final RequestClientOption DEFAULT_OPTION = newDefaultOption();

    private static RequestClientOption newDefaultOption() {
        return new ConstantRequestClientOption();
    }

    public static RequestClientOption copy(RequestClientOption origin) {
        RequestClientOption requestClientOption = new RequestClientOption();
        requestClientOption.setProxy(origin.getProxy());
        requestClientOption.setTimeout(origin.getTimeout());
        requestClientOption.setFollowUrlRedirect(origin.isFollowUrlRedirect());
        requestClientOption.setRetryable(origin.isRetryable());
        requestClientOption.setMaxRetryTimes(origin.getMaxRetryTimes());
        requestClientOption.setIgnoreSSLError(origin.isIgnoreSSLError());

        return requestClientOption;
    }

    public RequestClientOption copy() {
        RequestClientOption requestClientOption = new RequestClientOption();
        requestClientOption.setProxy(this.getProxy());
        requestClientOption.setTimeout(this.getTimeout());
        requestClientOption.setFollowUrlRedirect(this.isFollowUrlRedirect());
        requestClientOption.setRetryable(this.isRetryable());
        requestClientOption.setMaxRetryTimes(this.getMaxRetryTimes());
        requestClientOption.setIgnoreSSLError(this.isIgnoreSSLError());

        return requestClientOption;
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
        public boolean isRetryable() {
            return super.isRetryable();
        }

        @Override
        public boolean isFollowUrlRedirect() {
            return super.isFollowUrlRedirect();
        }

        @Override
        public void setProxy(Proxy proxy) {}

        @Override
        public void setTimeout(long timeout) {}

        @Override
        public void setRetryable(boolean retryable) {}

        @Override
        public void setMaxRetryTimes(int retryTimes) {}

        @Override
        public void setFollowUrlRedirect(boolean followUrlRedirect) {}
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

        public RequestClientOptionBuilder followUrlRedirect(boolean followUrlRedirect) {
            requestClientOption.setFollowUrlRedirect(followUrlRedirect);
            return this;
        }

        public RequestClientOptionBuilder retryable(boolean retryable) {
            requestClientOption.setRetryable(retryable);
            return this;
        }

        public RequestClientOptionBuilder maxRetryTimes(int maxRetryTimes) {
            requestClientOption.setMaxRetryTimes(maxRetryTimes);
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
