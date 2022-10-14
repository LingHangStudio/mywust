package cn.linghang.mywust.network;

import java.net.URL;

/**
 * <p>请求类的上层封装接口，传入参数直接进行请求</p>
 *
 * <p>实现了这个接口的类就可以提供给core使用</p>
 *
 * <p>例如，可以使用okhttp和httpclient实现底层网络请求（当然也可以自己实现类似的），在上层中只需要调用get或者post即可</p>
 *
 * @author lenfrex
 * @create 2022-10-14 20:13
 */
public interface Requester {

    HttpResponse get(URL url, HttpRequest request, RequestClientOption requestClientOption) throws Exception;

    HttpResponse post(URL url, HttpRequest request, RequestClientOption options) throws Exception;

    HttpResponse postJson(URL url, HttpRequest request, Object requestBody, RequestClientOption options) throws Exception;
}
