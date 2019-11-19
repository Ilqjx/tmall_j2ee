package tmall.servlet;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.Category;
import tmall.bean.Product;
import tmall.bean.PropertyValue;
import tmall.util.Page;

public class ProductServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid) ;
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		Date createDate = new Date();
		
		Product product = new Product();
		product.setCategory(category);
		product.setName(name);
		product.setSubTitle(subTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(stock);
		product.setCreateDate(createDate);
		
		productDAO.add(product);
		
		return "@admin_product_list?cid=" + cid;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(id);
		productDAO.delete(id);
		return "@admin_product_list?cid=" + product.getCategory().getId();
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(id);
		request.setAttribute("product", product);
		return "admin/editProduct.jsp";
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(id);
		
		String name = request.getParameter("name");
		String subTitle = request.getParameter("subTitle");
		float originalPrice = Float.parseFloat(request.getParameter("originalPrice"));
		float promotePrice = Float.parseFloat(request.getParameter("promotePrice"));
		int stock = Integer.parseInt(request.getParameter("stock"));
		
		product.setName(name);
		product.setSubTitle(subTitle);
		product.setOriginalPrice(originalPrice);
		product.setPromotePrice(promotePrice);
		product.setStock(stock);
		
		productDAO.update(product);
		
		return "@admin_product_list?cid=" + product.getCategory().getId();
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		int cid = Integer.parseInt(request.getParameter("cid"));
		Category category = categoryDAO.get(cid);
		List<Product> ps = productDAO.list(cid, page.getStart(), page.getCount());
		
		int total = productDAO.getTotal(cid);
		page.setTotal(total);
		page.setParam("&cid=" + cid);
		
		request.setAttribute("page", page);
		request.setAttribute("theps", ps);
		request.setAttribute("category", category);
		
		return "admin/listProduct.jsp";
	}
	
	public String editPropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int id = Integer.parseInt(request.getParameter("id"));
		Product product = productDAO.get(id);
		
		propertyValueDAO.init(product);
		List<PropertyValue> pvs = propertyValueDAO.list(id);
		
		request.setAttribute("pvs", pvs);
		request.setAttribute("product", product);
		
		return "admin/editPropertyValue.jsp";
	}
	
	public String updatePropertyValue(HttpServletRequest request, HttpServletResponse response, Page page) {
		int pvid = Integer.parseInt(request.getParameter("pvid"));
		String value = request.getParameter("value");
		
		PropertyValue propertyValue = propertyValueDAO.get(pvid);
		propertyValue.setValue(value);
		propertyValueDAO.update(propertyValue);
		
		return "%success";
	}

}
