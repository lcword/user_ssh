package com.user.page;

public class Page<T> {
	public static final int MAXCOUNT = 3;//每页最大数据量
	private int allCount;//总数据量
	private int maxPage;//最大页数
	private int curPage;//当前页
	private int prePage;//上一页
	private int nextPage;//后一页
	private T t;//泛型
	public Page(int allCount, int curPage) {
		this.allCount = allCount;
		this.curPage = curPage;
		getMaxPage();
	}
	public void setAllCount(int allCount) {
		this.allCount = allCount;
	}
	public int getAllCount() {
		return allCount;
	}
	/*求取最大页数*/
	public int getMaxPage() {
		maxPage = allCount / MAXCOUNT;
		if(allCount % MAXCOUNT != 0){
			maxPage += 1;
		}
		return maxPage;
	}
	public int getCurPage() {
		return curPage;
	}
	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}
	/*上一页*/
	public int getPrePage() {
		if(curPage > 1){
			prePage = curPage - 1;
		}else{
			prePage = curPage;
		}
		return prePage;
	}
	/*下一页*/
	public int getNextPage() {
		if(curPage < maxPage){
			nextPage = curPage + 1;
		}else{
			nextPage = curPage;
		}
		return nextPage;
	}
	/*开始位置*/
	public int getStartIndex() {
		return (curPage-1) * MAXCOUNT;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
}
