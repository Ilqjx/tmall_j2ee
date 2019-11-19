package tmall.filter;

import java.io.IOException;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import tmall.bean.Category;
import tmall.bean.OrderItem;
import tmall.bean.User;
import tmall.dao.CategoryDAO;
import tmall.dao.OrderItemDAO;

public class ForeServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		// ��ȡվ���·��
		String contextPath = request.getContextPath();
		// ��ȡ uri
		String uri = request.getRequestURI();
		uri = StringUtils.remove(uri, contextPath);
		
		// servletContext ���е� servlet �� jsp ��������ͬһ����
		request.getServletContext().setAttribute("contextPath", contextPath);

		// Ϊ simpleSearch.jsp �ṩ����
//		List<Category> cas = new CategoryDAO().list();
//		request.setAttribute("cas", cas);
		
		// Ϊ simpleSearch.jsp �ṩ����
		List<Category> cs = (List<Category>) request.getAttribute("cs");
		if (cs == null) {
			cs = new CategoryDAO().list();
			request.setAttribute("cs", cs);
		}
		
		User user = (User) request.getSession().getAttribute("user");
		int cartTotalItemNumber = 0;
		if (user != null) {
			List<OrderItem> ois = new OrderItemDAO().listByUser(user.getId());
			for (OrderItem oi : ois) {
				cartTotalItemNumber += oi.getNumber();
			}
		}
		request.setAttribute("cartTotalItemNumber", cartTotalItemNumber);
		
		// '/foreServlet' ·����Ҫ����
		// ���û�� !uri.startsWith("/foreServlet") �����·���ͻᱻ�������� �Ӷ��γ���ѭ��
		if (uri.startsWith("/fore") && !uri.startsWith("/foreServlet")) {
			String servletPath = "/foreServlet";
			String method = StringUtils.substringAfter(uri, "/fore");
			request.setAttribute("method", method);
			request.getRequestDispatcher(servletPath).forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

}
