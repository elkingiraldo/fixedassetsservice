package co.com.grupoasd.services.fixedassets.paging;

public class PageInformation {

	private Sort sort;
	private int pageSize;
	private int pageNumber;
	private int offset;
	private boolean unpaged;
	private boolean paged;

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public boolean isUnpaged() {
		return unpaged;
	}

	public void setUnpaged(boolean unpaged) {
		this.unpaged = unpaged;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean paged) {
		this.paged = paged;
	}

	@Override
	public String toString() {
		return "PageInformation [sort=" + sort + ", pageSize=" + pageSize + ", pageNumber=" + pageNumber + ", offset="
				+ offset + ", unpaged=" + unpaged + ", paged=" + paged + "]";
	}

}
