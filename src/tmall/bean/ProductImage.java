package tmall.bean;

public class ProductImage {

	private int id;
	private Product product; // 与 Product 的多对一关系
	private String type; // 图片的类型
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product ;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}
	
}
