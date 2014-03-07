package jon.bookstore.cart;

import java.io.IOException;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.bookstore.book.Book;
import jon.bookstore.book.BookService;
import jon.servlet.BaseServlet;

public class CartServlet extends BaseServlet {
	private BookService bookService = new BookService();
	
	public String showCart(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart != null) {
			Collection<CartItem> cartItems = cart.getCartItems();
			if (cartItems.size() > 0) {
				request.setAttribute("cartItems", cartItems);				
			}
		}
		return "/jsps/cart/cartlist.jsp";//多加了一级，为了过滤做准备！
	}
	
	
	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1 获取bid
		 * 2获取Book
		 * 3获取count
		 * 4CartItem(Book, count)，计算小计
		 * 5获取session中的Cart
		 * 6判断是否为空，若为空则创建一个Cart
		 * 7把CartItem添加到Cart中
		 * 8把Cart放到Session中
		 * ----------
		 * 去页面显示Cart
		 */
		String bid = request.getParameter("bid");
		Book book = bookService.load(bid);
		int count = Integer.parseInt(request.getParameter("count"));
		CartItem cartItem = new CartItem();
		cartItem.setBook(book);
		cartItem.setCount(count);
		cartItem.setSubtotal(book.getPrice() * count);
		
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart == null) {
			cart = new Cart();
		}
		cart.add(cartItem);
		request.getSession().setAttribute("cart", cart);
		
		
		return "/cart/CartServlet?method=showCart";
	}
	
	public String del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart != null) {
			cart.del(bid);;
		}
		return "/cart/CartServlet?method=showCart";
	}
	
	public String clear(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Cart cart = (Cart) request.getSession().getAttribute("cart");
		if (cart != null) {
			cart.clear();
		}
		return "/jsps/cart/cartlist.jsp";
	}
	
	
}
