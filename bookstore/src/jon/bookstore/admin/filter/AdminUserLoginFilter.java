package jon.bookstore.admin.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jon.bookstore.admin.adminuser.AdminUser;

@WebFilter({ "/admin/*", "/adminjsps/admin/*" })
public class AdminUserLoginFilter implements Filter {
	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		AdminUser adminUser = (AdminUser) session.getAttribute("adminuser");
		if (adminUser == null) {
			req.setAttribute("msg", "您还没有登录，不能进入后台管理");
			req.getRequestDispatcher("/adminjsps/msg.jsp").forward(req, response);
			return;
		}
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
