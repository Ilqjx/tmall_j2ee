package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Property;
import tmall.bean.PropertyValue;
import tmall.util.DBUtil;

public class PropertyValueDAO {

	public int getTotal() {
		String sql = "select count(*) from propertyvalue";
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
	
	public void add(PropertyValue propertyValue) {
		String sql = "insert into propertyvalue values(null, ?, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, propertyValue.getProduct().getId());
			ps.setInt(2, propertyValue.getProperty().getId());
			ps.setString(3, propertyValue.getValue());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				propertyValue.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(PropertyValue propertyValue) {
		String sql = "update propertyvalue set pid = ?, ptid =?, value = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, propertyValue.getProduct().getId());
			ps.setInt(2, propertyValue.getProperty().getId());
			ps.setString(3, propertyValue.getValue());
			ps.setInt(4, propertyValue.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from propertyvalue where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public PropertyValue get(int id) {
		String sql = "select * from propertyvalue where id = ?";
		PropertyValue propertyValue = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				propertyValue = new PropertyValue();
				propertyValue.setId(id);
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				propertyValue.setValue(rs.getString("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return propertyValue;
	}
	
	public PropertyValue get(int ptid, int pid) {
		String sql = "select * from propertyvalue where ptid = ? and pid = ?";
		PropertyValue propertyValue = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, ptid);
			ps.setInt(2, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				propertyValue = new PropertyValue();
				propertyValue.setId(rs.getInt("id"));
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				propertyValue.setValue(rs.getString("value"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return propertyValue;
	}
	
	public List<PropertyValue> list(int start, int count) {
		String sql = "select * from propertyvalue order by id desc limit ?, ?";
		List<PropertyValue> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int pid = rs.getInt("pid");
				int ptid = rs.getInt("ptid");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				PropertyValue propertyValue = new PropertyValue();
				propertyValue.setId(rs.getInt("id"));
				propertyValue.setProduct(product);
				propertyValue.setProperty(property);
				propertyValue.setValue(rs.getString("value"));
				list.add(propertyValue);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<PropertyValue> list() {
		return list(0, Short.MAX_VALUE);
	}
	
	// 初始化产品属性值
	public void init(Product product) {
		List<Property> propertys = new PropertyDAO().list(product.getCategory().getId());
		for (Property property : propertys) {
			PropertyValue pv = get(property.getId(), product.getId());
			if (null == pv) {
				pv = new PropertyValue();
				pv.setProduct(product);
				pv.setProperty(property);
				
				// this 代表类的实例
				this.add(pv);
			}
		}
	}
	
	public List<PropertyValue> list(int pid) {
		String sql = "select * from propertyvalue where pid = ? order by ptid desc";
		List<PropertyValue> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int ptid = rs.getInt("ptid");
				Product product = new ProductDAO().get(pid);
				Property property = new PropertyDAO().get(ptid);
				
				PropertyValue pv = new PropertyValue();
				pv.setId(rs.getInt("id"));
				pv.setProduct(product);
				pv.setProperty(property);
				pv.setValue(rs.getString("value"));
				
				list.add(pv);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
}
