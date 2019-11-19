package tmall.bean;

import java.util.List;

public class Category {

	private int id;
	private String name; // ��������
	// ���ڲ�ͬ��ҳ����ʾ
	private List<Product> products; // һ���Ե��õ���������µ�ȫ����Ʒ
	private List<List<Product>> productByRow; // ��������,���ϵļ���,���α���,������ʾ����ҳ��
	
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
