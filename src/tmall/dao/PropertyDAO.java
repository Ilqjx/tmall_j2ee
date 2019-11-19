package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.DBUtil;

public class PropertyDAO {

	// 获取某个分类下有多少个属性
	public int getTotal(int cid) {
		String sql = "select count(*) from property where cid = ?";
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
	
	public void add(Property property) {
		String sql = "insert into property values(null, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, property.getCategory().getId());
			ps.setString(2, property.getName());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				property.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Property property) {
		String sql = "update property set cid = ?, name = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, property.getCategory().getId());
			ps.setString(2, property.getName());
			ps.setInt(3, property.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from property where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Property get(int id) {
		String sql = "select * from property where id = ?";
		Property property = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int cid = rs.getInt("cid");
				Category category = new CategoryDAO().get(cid);
				
				property = new Property();
				property.setId(id);
				property.setCategory(category);
				property.setName(rs.getString("name"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return property;
	}
	
	// 获取一个分类下的属性
	public List<Property> list(int cid, int start, int count) {
		String sql = "select * from property where cid = ? order by id desc limit ?, ?";
		List<Property> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, cid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Category category = new CategoryDAO().get(cid); 
				
				Property property = new Property();
				property.setId(rs.getInt("id"));
				property.setName(rs.getString("name"));
				property.setCategory(category);
				list.add(property);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Property> list(int cid) {
		return list(cid, 0, Short.MAX_VALUE);
	}
	
}
