package tmall.bean;

import java.util.List;

public class Category {

	private int id;
	private String name; // 分类名称
	// 用于不同的页面显示
	private List<Product> products; // 一次性的拿到这个分类下的全部产品
	private List<List<Product>> productByRow; // 集合属性,集合的集合,二次遍历,方便显示分类页面
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public List<Product> getProducts() {
		return products;
	}
	
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	
	public List<List<Product>> getProductByRow() {
		return productByRow;
	}
	
	public void setProductByRow(List<List<Product>> productByRow) {
		this.productByRow = productByRow;
	}
	
	@Override
	public String toString() {
		return "Category [name=" + name + "]";
	}
	
}
