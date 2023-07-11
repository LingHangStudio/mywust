package cn.wustlinghang.mywust.core.util;

import cn.wustlinghang.mywust.network.entitys.HttpResponse;

import java.nio.charset.StandardCharsets;

/**
 * 专属于bkjx（本科教学）系统服务的工具类
 */
public class BkjxUtil {
    // 在“Bkjx”系统里边如果收到的响应开头是这个的话多半是cookie无效了，需要重新登录获取cookie
    private static final byte[] LOGIN_MESSAGE_RESPONSE_FINGER = "<script".getBytes(StandardCharsets.UTF_8);

    /**
     * <p>通过粗暴地比较响应字节前几个字符是否为登录跳转特征字符判断是否需要重新登录</p>
     * <p>只比较前若干个字节，在数据较大时比较有用</p>
     * <p>对于null对象，一律认为不需要</p>
     *
     * @param response 响应的字节
     * @return 是否需要重新登录
     */
    public static boolean needLogin(byte[] response) {
        if (response == null) {
            return false;
        }

        for (int i = 0; i < LOGIN_MESSAGE_RESPONSE_FINGER.length; i++) {
            if (LOGIN_MESSAGE_RESPONSE_FINGER[i] != response[i]) {
                return false;
            }
        }

        return true;
    }

    /**
     * <p>检查是否为专属选课时间封号</p>
     * <p>对于null对象，一律认为不是</p>
     *
     * @param response HttpResponse响应
     * @return 是否为封号响应
     */
    public static boolean isBannedResponse(HttpResponse response) {
        if (response == null || response.getBody() == null) {
            return false;
        }

        // 这里用了个取巧的办法，如果响应体大于400bytes，则直接认为不是封号的响应，不继续比较关键字
        if (response.getBody().length > 400) {
            return false;
        }

        return isBannedResponse(response.getStringBody());
    }

    /**
     * <p>检查是否为专属选课时间封号</p>
     * <p>对于null对象，一律认为不是</p>
     *
     * @param responseText 响应字符串
     * @return 是否为封号响应
     */
    public static boolean isBannedResponse(String responseText) {
        if (responseText == null) {
            return false;
        }

        return responseText.contains("当前登录帐号已被禁用");
    }
}
