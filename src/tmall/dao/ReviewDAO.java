package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Product;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class ReviewDAO {

	public int getTotal() {
		String sql = "select count(*) from review";
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
	
	public int getTotal(int pid) {
		String sql = "select count(*) from review where pid = ?";
		int total = 0;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public int getCount(int pid) {
		String sql = "select count(*) from review where pid = ?";
		int total = 0;
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, pid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				total = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return total;
	}
	
	public void add(Review review) {
		String sql = "insert into review values(null, ?, ?, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, review.getContent());
			ps.setInt(2, review.getUser().getId());
			ps.setInt(3, review.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				review.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Review review) {
		String sql = "update review set content = ?, uid = ?, pid = ?, createDate = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, review.getContent());
			ps.setInt(2, review.getUser().getId());
			ps.setInt(3, review.getProduct().getId());
			ps.setTimestamp(4, DateUtil.d2t(review.getCreateDate()));
			ps.setInt(5, review.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from review where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Review get(int id) {
		String sql = "select * from review where id = ?";
		Review review = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				
				review = new Review();
				review.setId(id);
				review.setContent(rs.getString("content"));
				review.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				review.setProduct(product);
				review.setUser(user);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return review;
	}
	
	// 取出一个产品下的全部评价
	public List<Review> list(int pid, int start, int count) {
		String sql = "select * from review where pid = ? order by id desc limit ?, ?";
		List<Review> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int uid = rs.getInt("uid");
				User user = new UserDAO().get(uid);
				Product product = new ProductDAO().get(pid);
				
				Review review = new Review();
				review.setId(rs.getInt("id"));
				review.setContent(rs.getString("content"));
				review.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				review.setProduct(product);
				review.setUser(user);
				
				list.add(review);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Review> list(int pid) {
		return list(pid, 0, Short.MAX_VALUE);
	}
	
	// 有时因为网络延迟,用户在提交评价的时候发现没有提交成功,就会多次点击提交按钮,其结果会导致同一个评论内容提交了多次。
	// 判断一个用户同一条评论是否提交了多次
	public boolean isExist(String content, int pid, int uid) {
		String sql = "select * from review where content = ? and pid = ? and uid = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, content);
			ps.setInt(2, pid);
			ps.setInt(3, uid);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
}
