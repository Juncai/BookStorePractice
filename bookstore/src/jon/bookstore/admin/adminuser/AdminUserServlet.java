package jon.bookstore.admin.adminuser;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.servlet.BaseServlet;

public class AdminUserServlet extends BaseServlet {
	private AdminUserService adminUserService = new AdminUserService();
	
	public String login(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		
		AdminUser adminUser = adminUserService.login(username, password);
		if (adminUser == null) {
			request.setAttribute("msg", "用户名或密码错误");
			return "/adminjsps/login.jsp";
		}
		request.getSession().setAttribute("adminuser", adminUser);
		
		response.sendRedirect(request.getContextPath() + "/adminjsps/admin/main.jsp");
		return null;
	}

}
