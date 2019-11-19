package tmall.bean;

public class Property {

	private int id;
	private String name; // 属性名称
	private Category category; // 因为要通过属性获取分类的名称所以没有用 cid 作为属性
	
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
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
}
