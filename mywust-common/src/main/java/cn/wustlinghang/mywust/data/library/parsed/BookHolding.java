package cn.wustlinghang.mywust.data.library.parsed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 馆藏信息（单个）
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookHolding {
    @JsonProperty("callNo")
    private String callNumber;

    /**
     * 馆藏总数
     */
    private int itemsCount;

    /**
     * 可借数
     */
    private int itemsAvailable;

    /**
     * 条形码
     */
    private String barCode;

    /**
     * 所属馆藏地
     */
    private String location;

    /**
     * 临时馆藏地
     */
    private String tempLocation;

    /**
     * 卷年期
     */
    @JsonProperty("vol")
    private String volume;

    /**
     * 馆名（总馆等等）
     */
    private String library;

    /**
     * 状态文本
     */
    private String status;
}
