package cn.wustlinghang.mywust.data.library.parsed;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * 书籍详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetail {
    private String title;
    private String author;
    private String isbn;

    /**
     * 作者介绍
     */
    private String authorDescribe;

    /**
     * 目录
     */
    private String catalog;

    /**
     * 封面url
     */
    private String coverUrl;

    /**
     * 图书简要
     */
    private String summary;

    /**
     * 图书介绍
     */
    private String introduction;

    /**
     * 书籍详细信息，因为图书馆系统奇葩的中文不定key，因此将这些信息放入key-value对中，需要者自取
     */
    Map<String, String> extraInfoMap;
}
