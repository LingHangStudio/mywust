package cn.linghang.mywust.core.request.factory.library.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class SearchResultItem {
    /**
     * 图书id
     */
    @JsonProperty("bibId")
    private String bookId;

    /**
     * 作者
     */
    @JsonProperty("author")
    private String author;

    /**
     * 索书号
     */
    @JsonProperty("callno")
    private List<String> callNumber;

    /**
     * doc类型
     */
    @JsonProperty("docType")
    private String docType;

    /**
     * 分组id
     */
    @JsonProperty("groupId")
    private String groupId;

    /**
     * ISBN号
     */
    @JsonProperty("isbn")
    private String isbn;

    @JsonProperty("bibNo")
    private String bibNo;

    /**
     * 标题
     */
    @JsonProperty("title")
    private String title;

    /**
     * 图书总数
     */
    @JsonProperty("itemCount")
    private int itemCount;

    /**
     * 可借数
     */
    @JsonProperty("circCount")
    private int circCount;

    /**
     * 出版年
     */
    @JsonProperty("pub_year")
    private String publishYear;

    /**
     * 分类号
     */
    @JsonProperty("classno")
    private String classNumber;

    /**
     * 出版商
     */
    @JsonProperty("publisher")
    private String publisher;

    /**
     * 馆藏信息，原始数据为json字符串
     */
    @JsonProperty("holdings")
    private String collectionInfo;

    /**
     * 馆藏信息
     */
    @Data
    public static class CollectionInfo {
        /**
         * 索书号
         */
        @JsonProperty("callNo")
        private String callNumber;

        /**
         * 馆藏总数
         */
        @JsonProperty("itemsCount")
        private int itemsCount;

        /**
         * 图书条码
         */
        @JsonProperty("barCode")
        private String barCode;

        /**
         * 可借状态
         */
        @JsonProperty("circStatus")
        private int circStatus;

        /**
         * 书籍分卷信息
         */
        @JsonProperty("vol")
        private String vol;

        /**
         * 图书可借状态
         */
        @JsonProperty("itemsAvailable")
        private int itemsAvailable;

        /**
         * 所属馆藏地
         */
        @JsonProperty("location")
        private String location;

        /**
         * 捐赠者id
         */
        @JsonProperty("donatorId")
        private Object donatorId;

        /**
         * 状态信息
         */
        @JsonProperty("status")
        private String status;
    }
}
