package tmall.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tmall.bean.User;
import tmall.util.Page;

public class LoginServlet extends BaseBackServlet {

	@Override
	public String add(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String delete(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String edit(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String update(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String list(HttpServletRequest request, HttpServletResponse response, Page page) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// ÅÐ¶ÏºóÌ¨µÇÂ¼ÊÇ·ñ³É¹¦
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		if (!"root".equals(name)) {
			request.setAttribute("errorMessage", "ÕËºÅ´íÎó");
			return "backLogin.jsp";
		}
		if (!"admin".equals(password)) {
			request.setAttribute("errorMessage", "ÃÜÂë´íÎó");
			return "backLogin.jsp";
		}
		
		User admin = new User();
		request.getSession().setAttribute("admin", admin);
		
//		return "@admin_category_list";
		return "@admin";
	}
	
	// ºóÌ¨ÍË³ö
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("admin");
		return "@backLogin.jsp";
	}

}
