package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.User;
import tmall.util.DBUtil;
import tmall.util.DateUtil;

public class OrderDAO {

	public static final String waitPay = "waitPay"; // 代付款
	public static final String waitDelivery = "waitDelivery"; //待发货
	public static final String waitConfirm = "waitConfirm"; // 待收货
	public static final String waitReview = "waitReview"; // 待评价
	public static final String finish = "finish"; // 完成
	public static final String delete = "delete"; // 删除
	
	public int getTotal() {
		String sql = "select count(*) from order_";
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
	
	public void add(Order order) {
		String sql = "insert into order_ values(null, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, order.getOrderCode());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getPost());
			ps.setString(4, order.getReceiver());
			ps.setString(5, order.getMobile());
			ps.setString(6, order.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(order.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(order.getConfirmDate()));
			ps.setInt(11, order.getUser().getId());
			ps.setString(12, order.getStatus());
			
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				order.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(Order order) {
		String sql = "update order_ set orderCode = ?, address = ?, post = ?, receiver = ?,"
				+ "mobile = ?, userMessage = ?, createDate = ?, payDate = ?, deliveryDate = ?,"
				+ "confirmDate = ?, uid = ?, status = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setString(1, order.getOrderCode());
			ps.setString(2, order.getAddress());
			ps.setString(3, order.getPost());
			ps.setString(4, order.getReceiver());
			ps.setString(5, order.getMobile());
			ps.setString(6, order.getUserMessage());
			ps.setTimestamp(7, DateUtil.d2t(order.getCreateDate()));
			ps.setTimestamp(8, DateUtil.d2t(order.getPayDate()));
			ps.setTimestamp(9, DateUtil.d2t(order.getDeliveryDate()));
			ps.setTimestamp(10, DateUtil.d2t(order.getConfirmDate()));
			ps.setInt(11, order.getUser().getId());
			ps.setString(12, order.getStatus());
			ps.setInt(13, order.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from order_ where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Order get(int id) {
		String sql = "select * from order_ where id = ?";
		Order order = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int uid = rs.getInt("uid");
				User user = new UserDAO().get(uid);
				
				order = new Order();
				order.setId(id);
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiver(rs.getString("receiver"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				order.setUser(user);
				order.setStatus(rs.getString("status"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return order;
	}
	
	public List<Order> list(int start, int count) {
		String sql = "select * from order_ order by id desc limit ?, ?";
		List<Order> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, start);
			ps.setInt(2, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int uid = rs.getInt("uid");
				User user = new UserDAO().get(uid);
				
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiver(rs.getString("receiver"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				order.setUser(user);
				order.setStatus(rs.getString("status"));
				
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Order> list() {
		return list(0, Short.MAX_VALUE);
	}
	
	// 查询指定用户的订单(去掉某种状态,一般是 "delete")
	public List<Order> list(int uid, String excludedStatus, int start, int count) {
		String sql = "select * from order_ where uid = ? and status != ? order by id desc limit ?, ?";
		List<Order> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, uid);
			ps.setString(2, excludedStatus);
			ps.setInt(3, start);
			ps.setInt(4, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User user = new UserDAO().get(uid);
				
				Order order = new Order();
				order.setId(rs.getInt("id"));
				order.setOrderCode(rs.getString("orderCode"));
				order.setAddress(rs.getString("address"));
				order.setPost(rs.getString("post"));
				order.setReceiver(rs.getString("receiver"));
				order.setMobile(rs.getString("mobile"));
				order.setUserMessage(rs.getString("userMessage"));
				order.setCreateDate(DateUtil.t2d(rs.getTimestamp("createDate")));
				order.setPayDate(DateUtil.t2d(rs.getTimestamp("payDate")));
				order.setDeliveryDate(DateUtil.t2d(rs.getTimestamp("deliveryDate")));
				order.setConfirmDate(DateUtil.t2d(rs.getTimestamp("confirmDate")));
				order.setUser(user);
				order.setStatus(rs.getString("status"));
				
				list.add(order);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<Order> list(int uid, String excludedStatus) {
		return list(uid, excludedStatus, 0, Short.MAX_VALUE);
	}
	
}
