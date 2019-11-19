package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ProductDAO {

	public int getTotal(int cid) {
		String sql = "select count(*) from product where cid = ?";
		int total = 0;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Product product) {
		String sql = "insert into product values(null, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, product.getName());
			ps.setString(2, product.getSubTitle());
			ps.setFloat(3, product.getOriginalPrice());
			ps.setFloat(4, product.getPromotePrice());
			ps.setInt(5, product.getStock());
			ps.setInt(6, product.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
			
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				product.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Product product) {
		String sql = "update product set name = ?, subTitle = ?, originalPrice = ?,"
				+ "promotePrice = ?, stock = ?, cid = ?, createDate = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, product.getName());
			ps.setString(2, product.getSubTitle());
			ps.setFloat(3, product.getOriginalPrice());
			ps.setFloat(4, product.getPromotePrice());
			ps.setInt(5, product.getStock());
			ps.setInt(6, product.getCategory().getId());
			ps.setTimestamp(7, DateUtil.d2t(product.getCreateDate()));
			ps.setInt(8, product.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from product where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Product get(int id) {
		String sql = "select * from product where id = ?";
		Product product = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);
				
				product = new Product();
				product.setId(id);
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setName(rs.getString("name"));
				product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				product.setCategory(category);
				
				setFirstProductImage(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return product;
	}
	
	// 获取一个分类下的全部产品
	public List<Product> list(int cid, int start, int count) {
		String sql = "select * from product where cid = ? order by id desc limit ?, ?";
		List<Product> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category category = new CategoryDAO().get(cid);
				
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setName(rs.getString("name"));
				product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				product.setCategory(category);
				
				setFirstProductImage(product);
				
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Product> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}
	
	public List<Product> list(int start, int count) {
		String sql = "select * from product limit ?, ?";
		List<Product> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);
				
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setName(rs.getString("name"));
				product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				product.setCategory(category);
				
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Product> list() {
		return list(0, Short.MAX_VALUE);
	}
	
	// 为分类填充产品
	public void fill(Category category) {
		List<Product> products = this.list(category.getId());
		category.setProducts(products);
	}
	
	public void fill(List<Category> categorys) {
		for (Category category : categorys) {
			fill(category);
		}
	}
	
	// 为所有分类设置 productByRow 属性
	public void fillByRow(List<Category> categorys) {
		// 每行 8 个产品
		int productNumberEachRow = 8;
		for (Category category : categorys) {
			List<List<Product>> productByRow = new ArrayList<>();
			List<Product> products = category.getProducts();
			for (int i = 0; i < products.size(); i += productNumberEachRow) {
				int size = i + productNumberEachRow;
				size = size > products.size() ? products.size() : size;
				List<Product> productsOfEachRow = products.subList(i, size);
				productByRow.add(productsOfEachRow);
			}
			category.setProductByRow(productByRow);
		}
	}
	
//	public void fillByRow(List<Category> categorys) {
//		int productNumberEachRow = 8;
//		for (Category category : categorys) {
//			List<List<Product>> productByRow = new ArrayList<>();
//			List<Product> products = category.getProducts();
//			List<Product> ps = new ArrayList<>();
//			int count = 0;
//			for (Product product : products) {
//				count++;
//				ps.add(product);
//				if (count == productNumberEachRow) {
//					productByRow.add(ps);
//					ps.clear();
//					count = 0;
//				}
//			}
//			if (count != productNumberEachRow) {
//				productByRow.add(ps);
//			}
//			category.setProductByRow(productByRow);
//		}
//	}
	
	// 把第一个图片设置为主图片
	public void setFirstProductImage(Product product) {
		List<ProductImage> productImages = new ProductImageDAO().list(product, ProductImageDAO.type_single);
		if (0 != productImages.size()) {
			product.setFirstProductImage(productImages.get(0));
		}
	}
	
	// 设置 saleCount 和 reviewCount 属性
	public void setSaleAndReviewNumber(Product product) {
		product.setSaleCount(new OrderItemDAO().getSaleCount(product.getId()));
		product.setReviewCount(new ReviewDAO().getCount(product.getId()));
	}
	
	// 搜索关键字
	public List<Product> search(String keyword, int start, int count) {
		String sql = "select * from product where name like ? limit ?, ?";
		List<Product> list = new ArrayList<>();
		if (null == keyword || 0 == keyword.trim().length()) {
			return list;
		}
		
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			// like 操作符
			// % 通配符
			ps.setString(1, "%" + keyword.trim() + "%");
			ps.setInt(2, start);
			ps.setInt(3, count);
			
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);
				
				Product product = new Product();
				product.setId(rs.getInt("id"));
				product.setSubTitle(rs.getString("subTitle"));
				product.setOriginalPrice(rs.getFloat("originalPrice"));
				product.setPromotePrice(rs.getFloat("promotePrice"));
				product.setStock(rs.getInt("stock"));
				product.setName(rs.getString("name"));
				product.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				product.setCategory(category);
				
				setFirstProductImage(product);
				
				list.add(product);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Product> search(String keyword) {
		return search(keyword, 0, Short.MAX_VALUE);
	}
	
}
