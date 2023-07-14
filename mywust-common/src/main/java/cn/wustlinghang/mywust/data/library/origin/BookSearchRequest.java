package cn.wustlinghang.mywust.data.library.origin;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class BookSearchRequest {
    private List<Object> filterFieldList = new ArrayList<>();
    private String collapseField = "groupId";
    private String sortType = "desc";
    private String indexName = "idx.opac";
    private String sortField = "relevance";

    private List<QueryFieldListItem> queryFieldList = new ArrayList<>(1);

    private int pageSize;
    private int page;

	public BookSearchRequest(String keyWord, int pageSize, int page) {
		this.pageSize = pageSize;
		this.page = page;
		this.queryFieldList.add(new QueryFieldListItem(keyWord));
	}

	@Data
	public static class QueryFieldListItem {
		private String field = "all";
		private String operator = "*";
        private int logic = 0;

        private List<String> values = new ArrayList<>(1);

		public QueryFieldListItem(String keyWord) {
			values.add(keyWord);
		}
	}

	public static class Builder {
		private int pageSize;
		private int page;
		private String keyword;

		private Builder() {
		}

		public Builder pageSize(int pageSize) {
			this.pageSize = pageSize;
			return this;
		}

		public Builder page(int page) {
			this.page = page;
			return this;
		}

		public Builder keyword(String keyword) {
			this.keyword = keyword;
			return this;
		}

		public BookSearchRequest build() {
			return new BookSearchRequest(keyword, pageSize, page);
		}
	}
}