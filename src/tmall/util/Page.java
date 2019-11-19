package tmall.util;

public class Page {

	private int start; // ��ʼλ��
	private int count; // ÿҳ��ʾ������
	private int total; // �ܹ��ж���������
	private String param;

	public Page(int start, int count) {
		this.start = start;
		this.count = count;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getStart() {
		return start;
	}
	
	public void setCount(int count) {
		this.count = count;
	}
	
	public int getCount() {
		return count;
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public int getTotal() {
		return total;
	}
	
	public void setParam(String param) {
		this.param = param;
	}
	
	public String getParam() {
		return param;
	}
	
	public boolean isHasPrevious() {
		if (start == 0) {
			return false;
		}
		return true;
	}
	
	public boolean isHasNext() {
		if (start == getLast()) {
			return false;
		}
		return true;
	}
	
	public int getLast() {
		int last;
		if (total % count == 0) {
			last = total - count;
		} else {
			last = total - total % count;
		}
//		last = total % count == 0 ? (total - count) : (total - total % count);
		return last;
	}
	
	public int getTotalPage() {
		int totalPage;
		if (total % count == 0) {
			totalPage = total / count;
		} else {
			totalPage = total / count + 1;
		}
		// ��� total = 0 �� totalPage = 0
		// totalPage = 0 �� page.end(adminPage.jsp) < 0 ����
		if (totalPage == 0) {
			totalPage = 1;
		}
		return totalPage;
	}
	
}
