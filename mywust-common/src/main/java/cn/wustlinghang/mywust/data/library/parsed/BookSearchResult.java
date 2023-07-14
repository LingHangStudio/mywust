package cn.wustlinghang.mywust.data.library.parsed;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BookSearchResult {
    private String bibId;

    private List<String> holdingTypes;
    private String author;

    private List<String> callNumber;

    /**
     * 文献类型
     */
    private String docType;
    private String groupId;
    private String isbn;

    /**
     * 图书馆系统中的图书编号
     */
    private String bibNo;

    private String title;

    /**
     * 图书总数
     */
    private int itemCount;

    /**
     * 在借数量
     */
    private int circCount;

    private String pubYear;

    private String classNumber;

    private String publisher;

    private List<BookHolding> holdings;
}

/*
bibId: 图书馆系统中的图书ID，用于唯一标识一本图书。
holdingTypes: 图书的持有类型，此处为“print”，表示该图书以纸质形式持有。
author: 作者，此处为“Walter Savitch著”，表示该图书的作者是Walter Savitch。
callno: 索书号，此处为["TP312JA/E26=4/1","TP312JA/E26=4/2"]，表示该图书的索书号是TP312JA/E26=4/1和TP312JA/E26=4/2。
docType: 文献类型，此处为“doc.02”，表示该图书的文献类型为“doc.02”。
groupId: 分组ID，此处为“1e24652f1d98f0586dc6468136f8eba6”，可能是用于图书分类的一个标识。
isbn: ISBN号，此处为“7115152888 (v. 1)”，表示该图书的ISBN号是7115152888，其中“(v. 1)”可能表示版本号。
bibNo: 图书馆系统中的图书编号，此处为“1800067256”。
title: 书名，此处为“Java : an introduction to problem solving & programming = Java : 程序设计与问题解决/ Walter Savitch著.”，表示该图书的书名是“Java : 程序设计与问题解决”，作者是Walter Savitch。
itemCount: 图书总数，此处为8，表示该图书馆系统中该图书的总数。
circCount: 图书当前在借数量，此处也为8，表示该图书当前在借数量为8。
pub_year: 出版年份，此处为“2006.”，表示该图书的出版年份是2006年。
classno: 分类号，此处为“TP312JA”，可能是用于图书分类的一个标识。
publisher: 出版商，此处为“Posts & Telecom Press”，表示该图书的出版商是Posts & Telecom Press。
holdings: 持有情况，此处为空字符串，可能是用于记录该图书的其他持有情况。
_id: 记录ID，此处为“544237”，可能是用于记录该图书的唯一标识。
 */