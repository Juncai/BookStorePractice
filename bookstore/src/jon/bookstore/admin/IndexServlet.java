package jon.bookstore.admin;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.servlet.BaseServlet;

public class IndexServlet extends BaseServlet {

	public String left(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/adminjsps/admin/left.jsp";
	}
	
	public String top(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/adminjsps/admin/top.jsp";
	}
	
	public String body(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/adminjsps/admin/body.jsp";
	}

}
