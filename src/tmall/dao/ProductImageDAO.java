package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.ProductImage;
import tmall.util.DBUtil;

public class ProductImageDAO {

	public static final String type_single = "type_single"; // µ¥¸öÍ¼Æ¬
	public static final String type_detail = "type_detail"; // ÏêÇéÍ¼Æ¬
	
	public int getTotal() {
		String sql = "select count(*) from productimage";
		int total = 0;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(ProductImage productImage) {
		String sql = "insert into productimage values(null, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, productImage.getProduct().getId());
			ps.setString(2, productImage.getType());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				productImage.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(ProductImage productImage) {
		String sql = "update productimage set pid = ?, type = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, productImage.getProduct().getId());
			ps.setString(2, productImage.getType());
			ps.setInt(3, productImage.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from productimage where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public ProductImage get(int id) {
		String sql = "select * from productimage where id = ?";
		ProductImage productImage = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int pid = rs.getInt("pid");
				Product product = new ProductDAO().get(pid);
				
				productImage = new ProductImage();
				productImage.setId(id);
				productImage.setProduct(product);
				productImage.setType(rs.getString("type"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return productImage;
	}
	
	public List<ProductImage> list(Product product, String type, int start, int count) {
		String sql = "select * from productimage where pid = ? and type = ? order by id desc limit ?, ?";
		List<ProductImage> list = new ArrayList<>();
//		List<ProductImage> list = null;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, product.getId());
			ps.setString(2, type);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				ProductImage productImage = new ProductImage();
				productImage.setId(rs.getInt("id"));
				productImage.setType(type);
				productImage.setProduct(product);
				list.add(productImage);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<ProductImage> list(Product product, String type) {
		return list(product, type, 0, Short.MAX_VALUE);
	}
}
