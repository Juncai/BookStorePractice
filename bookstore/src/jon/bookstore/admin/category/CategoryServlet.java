package jon.bookstore.admin.category;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jon.bookstore.category.Category;
import jon.bookstore.category.CategoryException;
import jon.bookstore.category.CategoryService;
import jon.servlet.BaseServlet;
import jon.utils.CommonUtils;

public class CategoryServlet extends BaseServlet {
	private CategoryService categoryService = new CategoryService();
	
	public String addPre(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		return "/adminjsps/admin/category/add.jsp";
	}
	
	public String desc(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		Category category = categoryService.findByCid(cid);
		request.setAttribute("category", category);
		return "/adminjsps/admin/category/desc.jsp";
	}
	
	public String all(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		/*
		 * 1使用service获取所有分类
		 * 2把所有分类保存到request
		 * 3转到all页面
		 */
		List<Category> categoryList = categoryService.all();
		request.setAttribute("list", categoryList);
		return "/adminjsps/admin/category/all.jsp";
	}

	public String add(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		category.setCid(CommonUtils.uuid());
		
		try {
			categoryService.add(category);
		} catch (CategoryException e) {
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			return "/adminjsps/admin/category/add.jsp";
		}
		request.setAttribute("msg", "添加分类成功！");
		return "/adminjsps/admin/msg.jsp";
	}
	
	public String del(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String cid = request.getParameter("cid");
		
		try {
			categoryService.del(cid);
		} catch (CategoryException e) {
			String msg = e.getMessage();
			request.setAttribute("msg", msg);
			Category category = categoryService.findByCid(cid);
			request.setAttribute("category", category);
			return "/adminjsps/admin/category/desc.jsp";
		}
		request.setAttribute("msg", "删除分类成功！");
		return "/adminjsps/admin/msg.jsp";
	}
	
	public String mod(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Category category = CommonUtils.toBean(request.getParameterMap(), Category.class);
		categoryService.mod(category);
		request.setAttribute("msg", "修改分类成功！");
		return "/adminjsps/admin/msg.jsp";
	}
}
