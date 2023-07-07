package cn.wustlinghang.mywust.core.request.factory.library.request;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Data
public class SearchRequest {
    private final List<Object> filterFieldList = new ArrayList<>();
    private final String collapseField = "groupId";
    private final String sortType = "desc";
    private final String indexName = "idx.opac";
    private final String sortField = "relevance";

    private final List<QueryFieldListItem> queryFieldList = new ArrayList<>(1);

    private int pageSize;
    private int page;

	public SearchRequest(String keyWord, int pageSize, int page) {
		this.pageSize = pageSize;
		this.page = page;
		this.queryFieldList.add(new QueryFieldListItem(keyWord));
	}

	@Data
	public static class QueryFieldListItem {
		private final String field = "all";
		private final String operator = "*";
        private final int logic = 0;

        private final List<String> values = new ArrayList<>(1);

		public QueryFieldListItem(String keyWord) {
			values.add(keyWord);
		}
	}
}