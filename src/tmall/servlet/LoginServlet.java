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
	
	// �жϺ�̨��¼�Ƿ�ɹ�
	public String login(HttpServletRequest request, HttpServletResponse response, Page page) {
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		
		if (!"root".equals(name)) {
			request.setAttribute("errorMessage", "�˺Ŵ���");
			return "backLogin.jsp";
		}
		if (!"admin".equals(password)) {
			request.setAttribute("errorMessage", "�������");
			return "backLogin.jsp";
		}
		
		User admin = new User();
		request.getSession().setAttribute("admin", admin);
		
//		return "@admin_category_list";
		return "@admin";
	}
	
	// ��̨�˳�
	public String logout(HttpServletRequest request, HttpServletResponse response, Page page) {
		request.getSession().removeAttribute("admin");
		return "@backLogin.jsp";
	}

}
