package cn.wustlinghang.mywust.data.library.origin;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * 响应回来的书籍信息（借阅历史，当前借阅，即将逾期等的书目字段）
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseLoanBook {
    protected String bibId;

    protected String title;

    protected String author;

    /**
     * 借书时间
     */
    protected String loanDate;

    protected String location;

    protected String barCode;

    /**
     * 图书属性信息
     */
    @JsonProperty("bibAttrs")
    protected BookAttribute bookAttribute;

    /**
     * 图书属性
     */
    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class BookAttribute {
        protected String isbn;

        protected String publisher;

        @JsonProperty("pub_year")
        protected String publishYear;

        protected String price;

        /**
         * 索书号
         */
        @JsonProperty("callno")
        protected String callNumber;
        
        /**
         * 书类编号
         */
        @JsonProperty("classno")
        protected String classNumber;
    }
}
