package jon.bookstore.admin.book;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.bookstore.book.Book;
import jon.bookstore.book.BookService;
import jon.bookstore.category.Category;
import jon.bookstore.category.CategoryService;
import jon.servlet.BaseServlet;
import jon.utils.CommonUtils;

public class BookServlet extends BaseServlet {
	private BookService bookService = new BookService();
	private CategoryService categoryService = new CategoryService();
	public String all(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 获取所有图书
		 * 保存到request中
		 * 转发到all.jsp
		 */
		List<Book> bookList = bookService.all();
		request.setAttribute("bookList", bookList);
		return "/adminjsps/admin/book/all.jsp";
	}
	
	public String desc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 从request中获取bid
		 * 通过bookService用bid获取book
		 * 保存到request中
		 * 转发到desc.jsp
		 */
		String bid = request.getParameter("bid");
		Book book = bookService.load(bid);
		List<Category> categoryList = categoryService.all();
		request.setAttribute("book", book);
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/book/desc.jsp";
	}

	public String mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Book book = CommonUtils.toBean(request.getParameterMap(), Book.class);
		String cid = request.getParameter("cid");
		Category category = new Category();
		category.setCid(cid); // 修改图书只需要cid，不需要cname，因此不用获取完整的Category对象
		book.setCategory(category);
		bookService.mod(book);
		request.setAttribute("msg", "图书信息修改成功！");
		return "/adminjsps/admin/msg.jsp";
		
	}
	
	public String del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bid = request.getParameter("bid");
		bookService.del(bid);
		request.setAttribute("msg", "图书删除成功！");
		return "/adminjsps/admin/msg.jsp";
	}
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		List<Category> categoryList = categoryService.all();
		request.setAttribute("categoryList", categoryList);
		return "/adminjsps/admin/book/add.jsp";
	}
}
