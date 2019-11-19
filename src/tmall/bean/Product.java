package tmall.bean;

import java.util.Date;
import java.util.List;

public class Product {

	private int id;
	private String name; // 产品名称
	private String subTitle; // 小标题
	private float originalPrice; // 原始价格
	private float promotePrice; // 优惠价格
	private int stock; // 库存
	private Date createDate; // 创建日期
	private Category category; // 与 Category 的多对一关系
	// 从 productSingleImages 集合中取出第一个作为显示这个产品的默认图片
	private ProductImage firstProductImage;
	private List<ProductImage> productImages; // 与 ProductImage 的一对多关系 就是这个产品的全部图片
	private List<ProductImage> productSingleImages; // 单个产品图片集合 后端用 
	private List<ProductImage> productDetailImages; // 详情产品图片集合 后端用
	private int reviewCount; // 评价数量
	private int saleCount; // 销量
	
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
	
	public String getSubTitle() {
		return subTitle;
	}
	
	public void setSubTitle(String subTitle) {
		this.subTitle = subTitle;
	}
	
	public float getOriginalPrice() {
		return originalPrice;
	}
	
	public void setOriginalPrice(float originalPrice) {
		this.originalPrice = originalPrice;
	}
	
	public float getPromotePrice() {
		return promotePrice;
	}
	
	public void setPromotePrice(float promotePrice) {
		this.promotePrice = promotePrice;
	}
	
	public int getStock() {
		return stock;
	}
	
	public void setStock(int stock) {
		this.stock = stock;
	}
	
	public Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public ProductImage getFirstProductImage() {
		return firstProductImage;
	}
	
	public void setFirstProductImage(ProductImage firstProductImage) {
		this.firstProductImage = firstProductImage;
	}
	
	public List<ProductImage> getProductImages() {
		return productImages;
	}
	
	public void setProductImages(List<ProductImage> productImages) {
		this.productImages = productImages;
	}
	
	public List<ProductImage> getProductSingleImages() {
		return productSingleImages;
	}
	
	public void setProductSingleImages(List<ProductImage> productSingleImages) {
		this.productSingleImages = productSingleImages;
	}
	
	public List<ProductImage> getProductDetailImages() {
		return productDetailImages;
	}
	
	public void setProductDetailImages(List<ProductImage> productDetailImages) {
		this.productDetailImages = productDetailImages;
	}
	
	public int getReviewCount() {
		return reviewCount;
	}
	
	public void setReviewCount(int reviewCount) {
		this.reviewCount = reviewCount;
	}
	
	public int getSaleCount() {
		return saleCount;
	}
	
	public void setSaleCount(int saleCount) {
		this.saleCount = saleCount;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
}
