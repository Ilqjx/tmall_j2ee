package tmall.bean;

public class PropertyValue {

	private int id;
	private Product product; // ���Ʒ�Ķ��һ��ϵ
	private Property property; // �����ԵĶ��һ��ϵ 
	private String value; // ����ֵ
	
	public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public void setProduct(Product product) {
		this.product = product;
	}
	
	public Property getProperty() {
		return property;
	}
	
	public void setProperty(Property property) {
		this.property = property;
	}
	
	public String getValue() {
		return value;
	}
	
	public void setValue(String value) {
		this.value = value;
	}
	
}
