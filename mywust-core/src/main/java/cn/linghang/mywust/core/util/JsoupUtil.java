package cn.linghang.mywust.core.util;

import org.jsoup.nodes.Element;

public class JsoupUtil {
    /**
     * 获取Element对象中的内容(text)，用途是对null判断，防止直接.text()产生空指针错误
     *
     * @param element 元素对象
     * @return 获取到的内容，如果element为null，则返回一个空的字符串
     */
    public static String getElementContext(Element element) {
        return element == null ? "" : element.text();
    }

    public static String getOuterHtml(Element element) {
        return element == null ? "" : element.outerHtml();
    }

    /**
     * 从Element中拿到指定的标签值
     *
     * @param element 元素对象
     * @param key     标签值的key
     * @return 相应的值，若element为空则返回空字符串
     */
    public static String getAttr(Element element, String key) {
        if (element == null) {
            return "";
        } else {
            return element.attr(key);
        }
    }

    /**
     * 从Element中拿到指定的文本内容
     *
     * @param element 元素对象
     * @return 相应的值，若element为空则返回空字符串
     */
    public static String getText(Element element) {
        if (element == null) {
            return "";
        } else {
            return element.ownText();
        }
    }

    /**
     * 从select类型的Element中拿取到已选中的选项值
     *
     * @param element 元素对象
     * @return 相应的值，若element为空则返回空字符串
     */
    public static String getSelectContent(Element element) {
        if (element == null) {
            return "";
        } else {
            return element.getElementsByAttributeValue("selected", "selected")
                    .get(0)
                    .ownText();
        }
    }
}
