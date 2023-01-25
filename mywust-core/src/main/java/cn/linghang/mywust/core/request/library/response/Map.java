package cn.linghang.mywust.core.request.library.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Map<S, S1> {
    @JsonProperty("baseInfo")
    private BaseInfo baseInfo;

    @JsonProperty("detailInfo")
    private DetailInfo detailInfo;

    @JsonProperty("extraInfo")
    private ExtraInfo extraInfo;

    @JsonProperty("score")
    private int score;

    @JsonProperty("_clickCount")
    private int clickCount;

    @JsonProperty("collected")
    private boolean collected;

    @JsonProperty("出版发行项")
    private String publisher;

    @JsonProperty("个人责任者")
    private String author;

    @JsonProperty("提要文摘附注")
    private String describe;

    @JsonProperty("ISBN及定价")
    private String isbn;

    @JsonProperty("载体形态项")
    private String size;

    @JsonProperty("学科主题")
    private String category;

    @JsonProperty("使用对象附注")
    private String reader;

    @JsonProperty("责任者附注")
    private String authorDescribe;

    @JsonProperty("中图法分类号")
    private String clcNumber;

    @JsonProperty("著录信息附注")
    private String publishNotes;

    @JsonProperty("丛编项")
    private String series;

    @JsonProperty("题名/责任者")
    private String fullTitle;

    @JsonProperty("title")
    private String title;
}