package cn.wustlinghang.mywust.network.okhttp;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

public class NoneRedirectInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        okhttp3.Request request = chain.request();
        Response response = chain.proceed(request);

//        String location = response.headers().get("Location");
//        if (location != null) {
//            System.out.println(("Request redirected to：" + location));
//        }

        return response;
    }
}