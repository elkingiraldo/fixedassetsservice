package co.com.grupoasd.services.fixedassets.dtos;

import java.util.List;

import co.com.grupoasd.services.fixedassets.paging.PageInformation;
import co.com.grupoasd.services.fixedassets.paging.Sort;

public class PageResponseDTO<T> {

	private List<T> Content;

	private Sort sort = new Sort();

	private long totalElements;

	private boolean last;

	private int totalPages;

	private boolean first;

	private long numberOfElements;

	private int size;

	private int number;

	private PageInformation pageable = new PageInformation();

	public PageResponseDTO() {
	}

	public PageResponseDTO(List<T> list, long totalElements) {
		this.Content = list;
	}

	public PageInformation getPageable() {
		return pageable;
	}

	public void setPageable(PageInformation pageable) {
		this.pageable = pageable;
	}

	public List<T> getContent() {
		return Content;
	}

	public void setContent(List<T> content) {
		Content = content;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public long getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(long totalElements) {
		this.totalElements = totalElements;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public long getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(long numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	@Override
	public String toString() {

		String contentType = "UNKNOWN";
		List<T> content = getContent();

		if (content.size() > 0) {
			contentType = content.get(0).getClass().getName();
		}

		return String.format("Page %s of %d containing %s instances", getNumber() + 1, getTotalPages(), contentType);
	}

}
