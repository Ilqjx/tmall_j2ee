package tmall.servlet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.util.HtmlUtils;

import tmall.bean.Category;
import tmall.bean.Order;
import tmall.bean.OrderItem;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.bean.Review;
import tmall.bean.User;
import tmall.comparator.ProductAllComparator;
import tmall.comparator.ProductDateComparator;
import tmall.comparator.ProductPriceComparator;
import tmall.comparator.ProductReviewComparator;
import tmall.comparator.ProductSaleCountComparator;
import tmall.dao.OrderDAO;
import tmall.dao.ProductImageDAO;
import tmall.util.Page;

public class ForeServlet extends BaseForeServlet {

	// ��ҳ
	public String home(HttpServletRequest request, HttpServletResponse response, Page page) {
		List<Category> cs = categoryDAO.list();
		productDAO.fill(cs);
		productDAO.fillByRow(cs);
		request.setAttribute("cs", cs);
		return "home.jsp";
	}
	
	// ע��ɹ�
	public String register(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		// ���˺����������Ž���ת��
		name = HtmlUtils.htmlEscape(name);
		
		boolean exist = userDAO.isExist(name);
		if (exist) {
			request.setAttribute("msg", "�û����Ѵ���");
			return "register.jsp";
		}

		User user = new User();
		user.setName(name);
		user.setPassword(password);
		userDAO.add(user);
		
		return "@registerSuccess.jsp";
	}
	
	// ��¼�ɹ�
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		
		boolean exist = userDAO.isExist(name);
		if (!exist) {
			request.setAttribute("msg", "���û���������");
			return "login.jsp";
		}
		
		User user = userDAO.get(name, password);
		if (user == null) {
			request.setAttribute("msg", "���벻��ȷ");
			return "login.jsp";
		}
		
		request.getSession().setAttribute("user", user);
		
		return "@forehome";
	}
	
	// �˳�
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("user");
		return "@forehome";
	}
	
	// ��Ʒҳ��
	public String product(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		productDAO.setSaleAndReviewNumber(product);
		product.setProductSingleImages(productImageDAO.list(product, ProductImageDAO.type_single));
		product.setProductDetailImages(productImageDAO.list(product, ProductImageDAO.type_detail));
		
		List<PropertyValue> pvs = propertyValueDAO.list(pid);
		List<Review> reviews = reviewDAO.list(pid);

		request.setAttribute("pvs", pvs);
		request.setAttribute("reviews", reviews);
		request.setAttribute("product", product);
		
		return "product.jsp";
	}
	
	// ����Ƿ��¼
	public String checkLogin(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "%fail";
		} else {
			return "%success";
		}
	}
	
	// ģ̬���� �ж��Ƿ��¼�ɹ�
	public String loginAjax(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		name = HtmlUtils.htmlEscape(name);
		
		boolean exist = userDAO.isExist(name);
		if (!exist) {
			return "%�û���������";
		}
		
		User user = userDAO.get(name, password);
		if (user == null) {
			return "%���벻��ȷ";
		}
		
		request.getSession().setAttribute("user", user);
		
		return "%success";
	}
	
	// ����ҳ��
	public String category(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		String sort = request.getParameter("sort");
		
		Category category = categoryDAO.get(cid);
		productDAO.fill(category);
		List<Product> ps = category.getProducts();
		for (Product p : ps) {
			productDAO.setSaleAndReviewNumber(p);
		}
		
		if (sort != null) {
			if (sort.equals("all")) {
				Collections.sort(ps, new ProductAllComparator());
			} else if (sort.equals("date")) {
				Collections.sort(ps, new ProductDateComparator());
			} else if (sort.equals("price")) {
				Collections.sort(ps, new ProductPriceComparator());
			} else if (sort.equals("review")) {
				Collections.sort(ps, new ProductReviewComparator());
			} else if (sort.equals("saleCount")) {
				Collections.sort(ps, new ProductSaleCountComparator());
			}
		}
		
		request.setAttribute("category", category);
		
		return "category.jsp";
	}
	
	// ����
	public String search(HttpServletRequest request, HttpServletResponse response, Page page) {
		String keyword = request.getParameter("keyword");
		List<Product> ps = productDAO.search(keyword, 0, 20);
		for (Product p : ps) {
			productDAO.setSaleAndReviewNumber(p);
		}
		request.setAttribute("ps", ps);
		return "searchResult.jsp";
	}
	
	// ��Ʒҳ�� ��������ť
	public String buyone(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product product = productDAO.get(pid);
		User user = (User) request.getSession().getAttribute("user");

		int oiid = 0;
		boolean exist = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == product.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				exist = true;
				oiid = oi.getId();
				break;
			}
		}
		
		if (!exist) {
			OrderItem orderItem = new OrderItem();
			orderItem.setNumber(num);
			orderItem.setProduct(product);
			orderItem.setUser(user);
			orderItemDAO.add(orderItem);
			oiid = orderItem.getId();
		}
		
		return "@forebuy?oiid=" + oiid;
	}

	// ����ҳ��
	public String buy(HttpServletRequest request, HttpServletResponse response, Page page) {
		String[] oiids = request.getParameterValues("oiid");
		List<OrderItem> ois = new ArrayList<>();
		double total = 0;
		for (String strid : oiids) {
			int oiid = Integer.parseInt(strid);
			OrderItem orderItem = orderItemDAO.get(oiid);
			total += orderItem.getProduct().getPromotePrice() * orderItem.getNumber();
			ois.add(orderItem);
		}
		request.getSession().setAttribute("ois", ois);
		request.setAttribute("total", total);
		return "buy.jsp";
	}
	
	// ���빺�ﳵ
	public String addCart(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		Product product = productDAO.get(pid);
		User user = (User) request.getSession().getAttribute("user");
		
		boolean exist = false;
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == product.getId()) {
				oi.setNumber(oi.getNumber() + num);
				orderItemDAO.update(oi);
				exist = true;
				return "%success";
			}
		}
		
		if (!exist) {
			OrderItem orderItem = new OrderItem();
			orderItem.setNumber(num);
			orderItem.setProduct(product);
			orderItem.setUser(user);
			orderItemDAO.add(orderItem);
			return "%success";
		}
		
		return "%fail";
	}
	
	// �ҵĹ��ﳵ
	public String cart(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		request.setAttribute("ois", ois);
		return "cart.jsp";
	}
	
	// �ı乺�ﳵ�ж����������
	public String changeOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "%fail";
		}
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		int num = Integer.parseInt(request.getParameter("num"));
		List<OrderItem> ois = orderItemDAO.listByUser(user.getId());
		for (OrderItem oi : ois) {
			if (oi.getProduct().getId() == pid) {
				oi.setNumber(num);
				orderItemDAO.update(oi);
				break;
			}
		}
		return "%success";
	}
	
	// ɾ�����ﳵ�еĶ�����
	public String deleteOrderItem(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		if (user == null) {
			return "%fail";
		}
		int oiid = Integer.parseInt(request.getParameter("oiid"));
		orderItemDAO.delete(oiid);
		return "%success";
	}

	// ���ɶ���
	public String createOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<OrderItem> orderItems = (List<OrderItem>) request.getSession().getAttribute("ois");
		// ��ֹ�������ǿյ�
		if (orderItems == null) {
			return "@login.jsp";
		}
		
		String address = request.getParameter("address");
		String post = request.getParameter("post");
		String receiver = request.getParameter("receiver");
		String mobile = request.getParameter("mobile");
		String userMessage = request.getParameter("userMessage");
		String status = OrderDAO.waitPay;
		Date createDate = new Date();
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		int random = (int) (Math.random() * 9000) + 1000;
		String orderCode = sdf.format(createDate) + random;
		
		Order order = new Order();
		order.setUser(user);
		order.setAddress(address);
		order.setPost(post);
		order.setReceiver(receiver);
		order.setMobile(mobile);
		order.setUserMessage(userMessage);
		order.setStatus(status);
		order.setCreateDate(createDate);
		order.setOrderCode(orderCode);
		order.setOrderItems(orderItems);
		
		orderDAO.add(order);
		
		float total = 0;
		for (OrderItem orderItem : order.getOrderItems()) {
			total += orderItem.getNumber() * orderItem.getProduct().getPromotePrice();
			orderItem.setOrder(order);
			orderItemDAO.update(orderItem);
		}
		
		return "@forealiPay?oid=" + order.getId() + "&total=" + total;
	}
	
	// ����ҳ��
	public String aliPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		float total = Float.parseFloat(request.getParameter("total"));
		Order order = orderDAO.get(oid);
		order.setTotal(total);
		request.setAttribute("order", order);
		return "aliPay.jsp";
	}
	
	// ����ɹ�ҳ��
	public String payed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		float total = Float.parseFloat(request.getParameter("total"));
		Order order = orderDAO.get(oid);
		order.setTotal(total);
		order.setStatus(OrderDAO.waitDelivery);
		order.setPayDate(new Date());
		orderDAO.update(order);
		request.setAttribute("order", order);
		
		// ���¿��
		orderItemDAO.fill(order);
		for (OrderItem orderItem : order.getOrderItems()) {
			Product product = orderItem.getProduct();
			product.setStock(product.getStock() - orderItem.getNumber());
			productDAO.update(product);
		}
		
		return "payed.jsp";
	}
	
	// �ҵĶ���
	public String bought(HttpServletRequest request, HttpServletResponse response, Page page) {
		User user = (User) request.getSession().getAttribute("user");
		List<Order> orders = orderDAO.list(user.getId(), OrderDAO.delete);
		orderItemDAO.fill(orders);
		request.setAttribute("orders", orders);
		return "bought.jsp";
	}
	
	// ���ö�����״̬Ϊɾ��
	public String deleteOrder(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(OrderDAO.delete);
		orderDAO.update(order);
		return "%success";
	}

	// �ҵĶ��� -> ȷ���ջ�
	public String confirmPay(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		orderItemDAO.fill(order);
		request.setAttribute("order", order);
		return "confirmPay.jsp";
	}

	// ȷ���ջ� -> ȷ��֧��
	public String orderConfirmed(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setConfirmDate(new Date());
		order.setStatus(OrderDAO.waitReview);
		orderDAO.update(order);
		return "orderConfirmed.jsp";
	}
	
	// ����ҳ��
	public String review(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		orderItemDAO.fill(order);
		
		OrderItem orderItem = order.getOrderItems().get(0);
		Product product = orderItem.getProduct();
		productDAO.setSaleAndReviewNumber(product);
		List<Review> reviews = reviewDAO.list(product.getId());
		
		request.setAttribute("order", order);
		request.setAttribute("product", product);
		request.setAttribute("reviews", reviews);
		return "review.jsp";
	}
	
	// �ύ����
	public String doreview(HttpServletRequest request, HttpServletResponse response, Page page) {
		int oid = Integer.parseInt(request.getParameter("oid"));
		Order order = orderDAO.get(oid);
		order.setStatus(OrderDAO.finish);
		orderDAO.update(order);
		
		String content = request.getParameter("content");
		content = HtmlUtils.htmlEscape(content);
		
		int pid = Integer.parseInt(request.getParameter("pid"));
		Product product = productDAO.get(pid);
		Date createDate = new Date();
		User user = (User) request.getSession().getAttribute("user");
		
		if (!reviewDAO.isExist(content, product.getId(), user.getId())) {
			Review review = new Review();
			review.setContent(content);
			review.setCreateDate(createDate);
			review.setProduct(product);
			review.setUser(user);
			
			reviewDAO.add(review);
		}
		
		return "@forereview?oid=" + oid;
	}
	
}
