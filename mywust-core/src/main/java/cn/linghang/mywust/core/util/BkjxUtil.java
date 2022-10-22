package cn.linghang.mywust.core.util;

import java.nio.charset.StandardCharsets;

/**
 * 专属于bkjx（本科教学）系统服务的工具类
 */
public class BkjxUtil {
    // 在“Bkjx”系统里边如果收到的响应开头是这个的话多半是cookie无效了，需要重新登录获取cookie
    private static final byte[] LOGIN_MESSAGE_RESPONSE_FINGER = "<script languge='javascript'>".getBytes(StandardCharsets.UTF_8);

    /**
     * <p>通过粗暴地比较响应字节前几个字符是否为登录跳转特征字符判断是否需要重新登录</p>
     * <p>对于null对象，一律认为不需要</p>
     *
     * @param response 响应的字节
     * @return 是否需要重新登录
     */
    public static boolean checkLoginFinger(byte[] response) {
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
}
