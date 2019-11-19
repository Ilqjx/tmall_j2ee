package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.User;
import tmall.util.DBUtil;

public class UserDAO {

	public int getTotal() {
		String sql = "select count(*) from user";
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
	
	public void add(User user) {
		String sql = "insert into user values(null, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				user.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(User user) {
		String sql = "update user set name = ?, password = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setInt(3, user.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from user where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public User get(int id) {
		String sql = "select * from user where id = ?";
		User user = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(id);
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	public List<User> list(int start, int count) {
		String sql = "select * from user order by id desc limit ?, ?";
		List<User> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				
				list.add(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<User> list() {
		return list(0, Short.MAX_VALUE);
	}
	
	// 返回某个用户名是否已经存在
	public boolean isExist(String name) {
		User user = get(name);
		return user != null;
	}
	
	public User get(String name) {
		String sql = "select * from user where name = ?";
		User user = null;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(name);
				user.setPassword(rs.getString("password"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
	// 判断账号密码是否正确
	public User get(String name, String password) {
		String sql = "select * from user where name = ? and password = ?";
		User user = null;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, name);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(name);
				user.setPassword(password);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return user;
	}
	
}
