package co.com.grupoasd.services.fixedassets.paging;

public class PagingInformation {

	private int start;
	private int limit;

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getLimit() {
		return limit;
	}

	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public String toString() {
		return "PagingInformation [start=" + start + ", limt=" + limit + "]";
	}

}
