package tmall.servlet;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import tmall.dao.CategoryDAO;
import tmall.dao.OrderDAO;
import tmall.dao.OrderItemDAO;
import tmall.dao.ProductDAO;
import tmall.dao.ProductImageDAO;
import tmall.dao.PropertyDAO;
import tmall.dao.PropertyValueDAO;
import tmall.dao.ReviewDAO;
import tmall.dao.UserDAO;
import tmall.util.Page;

public abstract class BaseBackServlet extends HttpServlet {

	public abstract String add(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String delete(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String edit(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String update(HttpServletRequest request, HttpServletResponse response, Page page);
	public abstract String list(HttpServletRequest request, HttpServletResponse response, Page page);
	
	protected CategoryDAO categoryDAO = new CategoryDAO();
	protected OrderDAO orderDAO = new OrderDAO();
	protected OrderItemDAO orderItemDAO = new OrderItemDAO();
	protected ProductDAO productDAO = new ProductDAO();
	protected ProductImageDAO productImageDAO = new ProductImageDAO();
	protected PropertyDAO propertyDAO = new PropertyDAO();
	protected PropertyValueDAO propertyValueDAO = new PropertyValueDAO();
	protected ReviewDAO reviewDAO = new ReviewDAO();
	protected UserDAO userDAO = new UserDAO();
	
	public void service(HttpServletRequest request, HttpServletResponse response) {
		try {
			// ��ȡ��ҳ��Ϣ
			int start = 0;
			int count = 5;
			try {
				start = Integer.parseInt(request.getParameter("page.start"));
			} catch (NumberFormatException e) {
				
			}
			try {
				count = Integer.parseInt(request.getParameter("page.count"));
			} catch (NumberFormatException e) {
				
			}
			Page page = new Page(start, count);
			
			// ����
			String method = (String) request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
			        javax.servlet.http.HttpServletResponse.class, Page.class);
			String redirect = (String) m.invoke(this, request, response, page);
			
			// ���ݷ����ķ���ֵ������Ӧ�ķ������ת���ͻ�����ת���߽���������ַ���
			if (redirect.startsWith("@")) {
				response.sendRedirect(redirect.substring(1));
			} else if (redirect.startsWith("%")) {
				response.getWriter().print(redirect.substring(1));
			} else {
				request.getRequestDispatcher(redirect).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// �׳� RuntimeException �쳣����
			// �׳��쳣�������Լ������� ������쳣����ʾ�ڽ�����
			throw new RuntimeException(e);
		}
	}
	
	public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
		InputStream is = null;
		try {
			// ���� FileItem ����Ĺ���
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// �������ϴ����ļ�����
			ServletFileUpload upload = new ServletFileUpload(factory);
			// �����ϴ��ļ���С������Ϊ 10 M
			factory.setSizeThreshold(1024 *10240);
			
			// ���� request ����,���ѱ��е�ÿһ���������װ��һ�� FileItem ����,������һ������������ FileItem ����� list ����
			List<FileItem> items = upload.parseRequest(request);
			// ������
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				// next() ��ȡ�����е���һ��Ԫ��
				FileItem item = iter.next();
				// isFormField() �����ж� FileItem ������װ��������һ����ͨ�ı����ֶ�,����һ���ļ����ֶ�,�������ͨ�ļ����ֶη���true,���򷵻�false
				if (!item.isFormField()) {
					// ��ȡ�ϴ��ļ��������ֽ���
					is = item.getInputStream();
				} else {
					// getFieldName ���ڷ��ر���ǩ name ���Ե�ֵ
					String paramName = item.getFieldName();
					// ���ڽ� FileItem �����б����������������һ���ַ�������
					String paramValue = item.getString();
					// tomcat ����Ĭ�ϲ��� ISO-8859-1 �ı��뷽��
					// ͨ����Ϊ UTF-8 ����ȴ�� tomcat �� ISO-8859-1������ֽ��лָ�,
					// �佫��������ͨ�� ISO-8859-1 ������ɶ���������,�ٽ����ֽ������� UTF-8 ���½���,
					// ���ձ� new String ���ַ���
					paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
					
					// ����
					params.put(paramName, paramValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	
}
