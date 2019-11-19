package tmall.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.User;
import tmall.util.DBUtil;

public class OrderItemDAO {

	public int getTotal() {
		String sql = "select count(*) from orderitem";
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
	
	public void add(OrderItem orderItem) {
		String sql = "insert into orderitem values(null, ?, ?, ?, ?)";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, orderItem.getProduct().getId());
			
			// 订单项在创建的时候没有订单
			if (null == orderItem.getOrder()) {
				ps.setInt(2, -1);
			} else {
				ps.setInt(2, orderItem.getOrder().getId());
			}
			
			ps.setInt(3, orderItem.getUser().getId());
			ps.setInt(4, orderItem.getNumber());
			
			ps.execute();
			ResultSet rs = ps.getGeneratedKeys();
			if (rs.next()) {
				orderItem.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void update(OrderItem orderItem) {
		String sql = "update orderitem set pid = ?, oid = ?, uid = ?, number = ? where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, orderItem.getProduct().getId());
			
			if (null == orderItem.getOrder()) {
				ps.setInt(2, -1);
			} else {
				ps.setInt(2, orderItem.getOrder().getId());
			}
			
			ps.setInt(3, orderItem.getUser().getId());
			ps.setInt(4, orderItem.getNumber());
			ps.setInt(5, orderItem.getId());
			
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void delete(int id) {
		String sql = "delete from orderitem where id = ?";
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ps.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public OrderItem get(int id) {
		String sql = "select * from orderitem where id = ?";
		OrderItem orderItem = null; 
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				int oid = rs.getInt("oid");
				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				
				orderItem = new OrderItem();
				orderItem.setId(id);
				orderItem.setNumber(rs.getInt("number"));
				orderItem.setProduct(product);
				orderItem.setUser(user);
				if (-1 != oid) {
					Order order = new OrderDAO().get(oid);
					orderItem.setOrder(order);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orderItem;
	}
	
	// 查询某个用户未生成订单的订单项(即购物车中的订单项)
	public List<OrderItem> listByUser(int uid, int start, int count) {
		String sql = "select * from orderitem where uid = ? and oid = -1 order by id desc limit ?, ?";
		List<OrderItem> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, uid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int pid = rs.getInt("pid");
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("id"));
				orderItem.setNumber(rs.getInt("number"));
				orderItem.setProduct(product);
				orderItem.setUser(user);
				
				list.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<OrderItem> listByUser(int uid) {
		return listByUser(uid, 0, Short.MAX_VALUE);
	}
	
	public List<OrderItem> listByOrder(int oid, int start, int count) {
		String sql = "select * from orderitem where oid = ? order by id desc limit ?, ?";
		List<OrderItem> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, oid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int pid = rs.getInt("pid");
				int uid = rs.getInt("uid");
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				Order order = new OrderDAO().get(oid);
				
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("id"));
				orderItem.setNumber(rs.getInt("number"));
				orderItem.setProduct(product);
				orderItem.setUser(user);
				orderItem.setOrder(order);
				
				list.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<OrderItem> listByOrder(int oid) {
		return listByOrder(oid, 0, Short.MAX_VALUE);
	}
	
	// 为订单设置订单项集合
	public void fill(List<Order> orders) {
		for (Order order : orders) {
			List<OrderItem> orderItems = listByOrder(order.getId());
			float total = 0;
			int totalNumber = 0;
			for (OrderItem orderItem : orderItems) {
				total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
				totalNumber += orderItem.getNumber();
			}
			order.setTotal(total);
			order.setTotalNumber(totalNumber);
			order.setOrderItems(orderItems);
		}
	}
	
	public void fill(Order order) {
		List<OrderItem> orderItems = listByOrder(order.getId());
		float total = 0;
		int totalNumber = 0;
		for (OrderItem orderItem : orderItems) {
			total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			totalNumber += orderItem.getNumber();
		}
		order.setTotal(total);
		order.setTotalNumber(totalNumber);
		order.setOrderItems(orderItems);
	}
	
	public List<OrderItem> listByProduct(int pid, int start, int count) {
		String sql = "select * from orderitem where pid = ? order by id desc limit ?, ?";
		List<OrderItem> list = new ArrayList<>();
		try (Connection c = DBUtil.getConnection();
				PreparedStatement ps = c.prepareStatement(sql);) {
			ps.setInt(1, pid);
			ps.setInt(2, start);
			ps.setInt(3, count);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				int uid = rs.getInt("uid");
				int oid = rs.getInt("oid");
				Product product = new ProductDAO().get(pid);
				User user = new UserDAO().get(uid);
				
				OrderItem orderItem = new OrderItem();
				orderItem.setId(rs.getInt("id"));
				orderItem.setNumber(rs.getInt("number"));
				orderItem.setProduct(product);
				orderItem.setUser(user);
				if (-1 != oid) {
					Order order = new OrderDAO().get(oid);
					orderItem.setOrder(order);
				}
				
				list.add(orderItem);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	public List<OrderItem> listByProduct(int pid) {
		return listByProduct(pid, 0, Short.MAX_VALUE);
	}
	
	// 获取销量
	public int getSaleCount(int pid) {
		String sql = "select sum(number) from orderitem where pid = ?";
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

}
