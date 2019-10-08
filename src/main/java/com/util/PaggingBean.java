package com.util;

public class PaggingBean {

	private int pageIndex=1;
	private int pageSize=10;
	private int totalPage;
	
	public void initPagging(int totalCount){
		totalPage=totalCount%pageSize==0?(totalCount/pageSize):(totalCount/pageSize)+1;
		
		if(pageIndex>totalPage){
			pageIndex=totalPage;
		}
		
		if(pageIndex<1){
			pageIndex=1;
		}
	}
	
	public int first(){
		return (pageIndex-1)*pageSize;
	}
	
	public int last(){
		return pageSize;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
