package jon.bookstore.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import jon.bookstore.user.User;

@WebFilter({ "/cart/*", "/jsps/cart/*" })  // 这句话居然是必要的！！！！
public class LoginFilter implements Filter {
	public LoginFilter() {}
	public void destroy() {}

	public void doFilter(ServletRequest req, ServletResponse res,
			FilterChain chain) throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		User user = (User) request.getSession().getAttribute("user");

		if (user == null) {
			request.setAttribute("msg", "您还没有登录，不能放你过去！");
			List<String> links = new ArrayList<String>();
			links.add("<a href='" + request.getContextPath()
					+ "/index.jsp' target='_parent'>主页</a>");
			links.add("<a href='" + request.getContextPath()
					+ "/jsps/login.jsp' target='_parent'>登录</a>");
			request.setAttribute("links", links);
			request.getRequestDispatcher("/jsps/msg.jsp").forward(request, res);
			return;
		}
		
		chain.doFilter(request, res);
	}


	public void init(FilterConfig fConfig) throws ServletException {}

}



