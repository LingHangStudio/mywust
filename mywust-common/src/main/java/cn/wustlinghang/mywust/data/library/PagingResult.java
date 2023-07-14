package cn.wustlinghang.mywust.data.library;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PagingResult<T> {
    private int currentPage;
    private int pageSize;
    private int totalPage;

    private int totalResult;

    private T data;
}