package jon.bookstore.admin.order;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.bookstore.order.Order;
import jon.bookstore.order.OrderService;
import jon.servlet.BaseServlet;

public class OrderServlet extends BaseServlet {
	OrderService orderService = new OrderService();

	public String all(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1获取所有订单
		 * 2保存到request中
		 * 3转发到orderlist.jsp
		 */
		List<Order> orderList = orderService.findAll();
		request.setAttribute("orderList", orderList);
		return "/adminjsps/admin/order/orderlist.jsp";
	}
	
	public String findByState(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		int state = Integer.parseInt(request.getParameter("state"));
		List<Order> orderList = orderService.findByState(state);
		request.setAttribute("orderList", orderList);
		return "/adminjsps/admin/order/orderlist.jsp";
	}
	
	public String updateDeliver(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
			String oid = request.getParameter("oid");
			orderService.updateState(oid, 3);
			request.setAttribute("msg", "发货成功");
		return "/adminjsps/admin/msg.jsp";
	}

}
