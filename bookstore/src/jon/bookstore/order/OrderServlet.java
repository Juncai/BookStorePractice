package jon.bookstore.order;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.bookstore.cart.Cart;
import jon.bookstore.cart.CartItem;
import jon.bookstore.user.User;
import jon.servlet.BaseServlet;
import jon.utils.CommonUtils;

public class OrderServlet extends BaseServlet {
	private OrderService orderService = new OrderService();

	public String updateRec(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String oid = request.getParameter("oid");
		orderService.updateState(oid, 4);
		request.setAttribute("msg", "确认收货成功，后悔已经来不及鸟！");
		return "/jsps/msg.jsp";
	}
	
	public String orderDesc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1获取oid
		 * 2通过oid得到Order对象（OrderService）
		 * 3保存到request中
		 * 4转发到orderdesc.jsp
		 */
		String oid = request.getParameter("oid");
		Order order = orderService.findByOid(oid);
		request.setAttribute("order", order);
		return "/jsps/order/orderdesc.jsp";
	}
	
	
	public String myOrders(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 从request中获取user
		 * 使用user.uid查询所有order
		 * 将orderList存到request中
		 * 转发到orderlist。jsp
		 */
		User user = (User) request.getSession().getAttribute("user");
		List<Order> orderList = orderService.findByUid(user.getUid());
		request.setAttribute("orderList", orderList);
		return "/jsps/order/orderlist.jsp";
	}
	
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1从Session中获取Cart
		 * 2判断Cart是否为空，如果为空转发到msg.jsp
		 * 3判断Cart中是否有CartItem，如果没有内容，也转发到msg.jsp
		 * 
		 * 4创建Order对象
		 * 5为其指定oid
		 * 6获取cart的total，赋给order的total
		 * 7使用当前时间赋给order的ordertime
		 * 8设置order的状态为1，表示未付款
		 * 9地址这里不能负值，需要等待用户填写
		 * 10从session中获取User，赋给order对象
		 * 
		 * 11获取cart中所有cartItem
		 * 12循环遍历之
		 * 13创建OrderItem，并设置itemid
		 * 14获取cartItem的count，赋值orderItem的count
		 * 15获取cartItem的subtotal，赋给orderitem
		 * 16获取cartItem的book，赋给orderItem
		 * 17把当前订单再赋给orderitem
		 * 18把orderItem添加到Order中
		 * 
		 * 19使用OrderService保存Order对象
		 * 20把当前Order对象保存到request中，转发到orderdesc.jsp
		 */
		
		//1从Session中获取Cart
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		//2判断Cart是否为空，如果为空转发到msg.jsp
		//3判断Cart中是否有CartItem，如果没有内容，也转发到msg.jsp
		if (cart == null || cart.getCartItems().size() == 0) {
			request.setAttribute("msg", "购物车是空的，无法生成订单，也不知道您怎么来到这里的！");
			return "/jsps/msg.jsp";
		}
		//4创建Order对象
		Order order = new Order();
		// 5为其指定oid
		order.setOid(CommonUtils.uuid());
		// * 6获取cart的total，赋给order的total
		order.setTotal(cart.getTotal());
		// * 7使用当前时间赋给order的ordertime
		order.setOrdertime(new Date());
		// * 8设置order的状态为1，表示未付款
		order.setState(1);
		// * 9地址这里不能负值，需要等待用户填写
		// * 10从session中获取User，赋给order对象
		User user= (User) request.getSession().getAttribute("user");
		// 为了代码健壮性，即使有了过滤器来防止未登录用户访问，还是写如下的判断语句
		if (user == null) {
			request.setAttribute("msg", "您还没有登录，也不知道您怎么来到这里的！");
			return "/jsps/msg.jsp";
		}
		order.setUser(user);
		// *
		// * 11获取cart中所有cartItem
		Collection<CartItem> cartItems = cart.getCartItems();
		// * 12循环遍历之
		// * 13创建OrderItem，并设置itemid
		// * 14获取cartItem的count，赋值orderItem的count
		// * 15获取cartItem的subtotal，赋给orderitem
		// * 16获取cartItem的book，赋给orderItem
		// * 17把当前订单再赋给orderitem
		// * 18把orderItem添加到Order中
		for (CartItem item : cartItems) {
			OrderItem orderItem = new OrderItem();
			orderItem.setItemid(CommonUtils.uuid());
			orderItem.setCount(item.getCount());
			orderItem.setSubtotal(item.getSubtotal());
			orderItem.setBook(item.getBook());
			// 双向关联
			orderItem.setOrder(order);
			order.addOrderItem(orderItem);
		}
		// *
		// * 19使用OrderService保存Order对象
		orderService.add(order);
		// 清除购物车中的内容！
		cart.clear();
		// * 20把当前Order对象保存到request中，转发到orderdesc.jsp
		request.setAttribute("order", order);
		
		return "/jsps/order/orderdesc.jsp";
	}

}
