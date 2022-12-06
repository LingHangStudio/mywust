package cn.linghang.mywust.core.request.library.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class LoanResultItem {
    @JsonProperty("bibId")
    private String bookId;

    @JsonProperty("bibAttrs")
    private BookAttribute bookAttribute;

    @JsonProperty("returnDate")
    private String returnDate;

    @JsonProperty("loanDate")
    private String loanDate;

    @JsonProperty("location")
    private String location;

    @JsonProperty("barCode")
    private String barCode;

    @Data
    public static class BookAttribute {
        @JsonProperty("pub_year")
        private String publishYear;

        @JsonProperty("author")
        private String author;

        @JsonProperty("callno")
        private String callNumber;

        @JsonProperty("isbn")
        private String isbn;

        @JsonProperty("classno")
        private String classNumber;

        @JsonProperty("publisher")
        private String publisher;

        @JsonProperty("title")
        private String title;
    }
}