package cn.linghang.mywust.network;

import java.io.IOException;
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

    /**
     * 发送Get请求
     *
     * @param url                 请求地址
     * @param request             请求体
     * @param requestClientOption 请求选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse get(URL url, HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    /**
     * 发送Post请求
     *
     * @param url                 请求地址
     * @param request             请求体
     * @param requestClientOption 请求选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse post(URL url, HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    /**
     * 发送Post请求，并将对象自动封装成json
     *
     * @param url                 请求地址
     * @param request             请求体
     * @param requestClientOption 请求选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse postJson(URL url, HttpRequest request, Object requestBody, RequestClientOption requestClientOption) throws IOException;

    /**
     * 发送Put请求
     *
     * @param url                 请求地址
     * @param request             请求体
     * @param requestClientOption 请求选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse put(URL url, HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    /**
     * 发送Delete请求
     *
     * @param url                 请求地址
     * @param request             请求体
     * @param requestClientOption 请求选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse delete(URL url, HttpRequest request, RequestClientOption requestClientOption) throws IOException;
}
