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
			// 获取分页信息
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
			
			// 反射
			String method = (String) request.getAttribute("method");
			Method m = this.getClass().getMethod(method, javax.servlet.http.HttpServletRequest.class,
			        javax.servlet.http.HttpServletResponse.class, Page.class);
			String redirect = (String) m.invoke(this, request, response, page);
			
			// 根据方法的返回值进行相应的服务端跳转、客户端跳转或者仅仅是输出字符串
			if (redirect.startsWith("@")) {
				response.sendRedirect(redirect.substring(1));
			} else if (redirect.startsWith("%")) {
				response.getWriter().print(redirect.substring(1));
			} else {
				request.getRequestDispatcher(redirect).forward(request, response);
			}
		} catch (Exception e) {
			e.printStackTrace();
			// 抛出 RuntimeException 异常对象
			// 抛出异常而不是自己消化掉 如果有异常会显示在界面上
			throw new RuntimeException(e);
		}
	}
	
	public InputStream parseUpload(HttpServletRequest request, Map<String, String> params) {
		InputStream is = null;
		try {
			// 创建 FileItem 对象的工厂
			DiskFileItemFactory factory = new DiskFileItemFactory();
			// 负责处理上传的文件数据
			ServletFileUpload upload = new ServletFileUpload(factory);
			// 设置上传文件大小的限制为 10 M
			factory.setSizeThreshold(1024 *10240);
			
			// 解析 request 对象,并把表单中的每一个输入项包装成一个 FileItem 对象,并返回一个保存了所有 FileItem 对象的 list 集合
			List<FileItem> items = upload.parseRequest(request);
			// 迭代器
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				// next() 获取序列中的下一个元素
				FileItem item = iter.next();
				// isFormField() 用于判断 FileItem 类对象封装的数据是一个普通文本表单字段,还是一个文件表单字段,如果是普通文件表单字段返回true,否则返回false
				if (!item.isFormField()) {
					// 获取上传文件的输入字节流
					is = item.getInputStream();
				} else {
					// getFieldName 用于返回表单标签 name 属性的值
					String paramName = item.getFieldName();
					// 用于将 FileItem 对象中保存的数据流内容以一个字符串返回
					String paramValue = item.getString();
					// tomcat 容器默认采用 ISO-8859-1 的编码方法
					// 通过本为 UTF-8 编码却被 tomcat 用 ISO-8859-1解码的字进行恢复,
					// 其将解码后的字通过 ISO-8859-1 反解码成二进制数组,再将该字节数组用 UTF-8 重新解码,
					// 最终被 new String 成字符串
					paramValue = new String(paramValue.getBytes("ISO-8859-1"), "UTF-8");
					
					// 插入
					params.put(paramName, paramValue);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return is;
	}
	
}
