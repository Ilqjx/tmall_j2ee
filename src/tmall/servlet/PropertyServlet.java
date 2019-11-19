package tmall.servlet;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Property;
import tmall.util.Page;

public class PropertyServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		
		String name = request.getParameter("name");
		Property property = new Property();
		property.setName(name);
		property.setCategory(category);
		propertyDAO.add(property);
		
		return "@admin_property_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		propertyDAO.delete(id);
		return "@admin_property_list?cid=" + property.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		
		request.setAttribute("property", property);
		
		return "admin/editProperty.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Property property = propertyDAO.get(id);
		
		String name = request.getParameter("name");
		property.setName(name);
		propertyDAO.update(property);
		
		return "@admin_property_list?cid=" + property.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		List<Property> ps = propertyDAO.list(cid, page.getStart(), page.getCount());
		
		int total = propertyDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + cid);
		
		request.setAttribute("theps", ps);
		request.setAttribute("page", page);
		request.setAttribute("category", category);
		
		return "admin/listProperty.jsp";
	}

}
