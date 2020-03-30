package model;

public class Pagination {
	private static final int NUM_OF_NAVI_PAGE = 5;
	private static final int NUM_OF_MESSAGE_PER_PAGE = 10;
	
	private int calPageTotalCount;
	private int StartPage;
	private int EndPage;
	private int FirstRow;
	private int endRow;
	
	public int getCalPageTotalCount() {
		return calPageTotalCount;
	}
	public void setCalPageTotalCount(int calPageTotalCount) {
		this.calPageTotalCount = calPageTotalCount;
	}
	public int getStartPage() {
		return StartPage;
	}
	public void setStartPage(int startPage) {
		StartPage = startPage;
	}
	public int getEndPage() {
		return EndPage;
	}
	public void setEndPage(int endPage) {
		EndPage = endPage;
	}
	public int getFirstRow() {
		return FirstRow;
	}
	public void setFirstRow(int firstRow) {
		FirstRow = firstRow;
	}
	public int getEndRow() {
		return endRow;
	}
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}
	
	
	
	
}
