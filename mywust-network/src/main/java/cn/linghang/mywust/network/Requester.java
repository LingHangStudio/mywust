package cn.linghang.mywust.network;

import cn.linghang.mywust.network.entitys.HttpRequest;
import cn.linghang.mywust.network.entitys.HttpResponse;

import java.io.IOException;

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
     * @param request             请求体
     * @param requestClientOption 请求选项，为null时，使用默认的选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse get(HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    HttpResponse get(HttpRequest request) throws IOException;

    /**
     * 发送Post请求
     *
     * @param request             请求体
     * @param requestClientOption 请求选项，为null时，使用默认的选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse post(HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    HttpResponse post(HttpRequest request) throws IOException;

    /**
     * 发送Put请求
     *
     * @param request             请求体
     * @param requestClientOption 请求选项，为null时，使用默认的选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse put(HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    HttpResponse put(HttpRequest request) throws IOException;

    /**
     * 发送Delete请求
     *
     * @param request             请求体
     * @param requestClientOption 请求选项，为null时，使用默认的选项
     * @return 响应数据
     * @throws IOException 如果网络请求有异常
     */
    HttpResponse delete(HttpRequest request, RequestClientOption requestClientOption) throws IOException;

    HttpResponse delete(HttpRequest request) throws IOException;
}
