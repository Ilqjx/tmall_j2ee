package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Order;
import tmall.dao.OrderDAO;
import tmall.util.Page;

public class OrderServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Order> orders = orderDAO.list(page.getStart(), page.getCount());
		int total = orderDAO.getTotal();
		page.setTotal(total);
		
		orderItemDAO.fill(orders);
		
		request.setAttribute("page", page);
		request.setAttribute("orders", orders);
		
		return "admin/listOrder.jsp";
	}

	public String delivery(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Order order = orderDAO.get(id);
		order.setDeliveryDate(new Date());
		order.setStatus(OrderDAO.waitConfirm);
		orderDAO.update(order);
		return "@admin_order_list";
	}
	
}