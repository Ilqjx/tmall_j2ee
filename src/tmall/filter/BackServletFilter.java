package tmall.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

public class BackServletFilter implements Filter {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;
		
		// ��ȡվ��ĸ�Ŀ¼
		String contextPath = request.getContextPath();
		// ��ȡ uri
		String uri = request.getRequestURI();
		// �� uri ���Ƴ� contextPath 
		uri = StringUtils.remove(uri, contextPath);
		if (uri.startsWith("/admin_")) {
			String servletPath = StringUtils.substringBetween(uri, "_", "_") + "Servlet";
			String method = StringUtils.substringAfterLast(uri, "_");
			request.setAttribute("method", method);
			request.getRequestDispatcher("/" + servletPath).forward(request, response);
			// return ������ ��ֹ���η���
			return;
		}
		
		// ����������
		chain.doFilter(request, response);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}
	
}
