package jon.bookstore.admin.book;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;

import jon.bookstore.book.Book;
import jon.bookstore.book.BookService;
import jon.bookstore.category.Category;
import jon.bookstore.category.CategoryService;
import jon.utils.CommonUtils;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

public class AddBookServlet extends HttpServlet {

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService categoryService = new CategoryService();
		List<Category> categoryList = categoryService.all();
		
		/*
		 * 因为要上传，所以要使用fileupload 第一步：创建一个工厂 第二步：创建解析器，需要使用工厂
		 * 第三步：使用解析器来解析request，得到单项的集合
		 */
		// 获取项目真实路径，准备在其中创建一个temp文件夹，用于存放临时文件
		String temp = this.getServletContext().getRealPath("/temp");
		File tempDir = new File(temp);
		// 创建工厂，并规定上传文件若大于20kb，则存放到临时文件夹file中！
		DiskFileItemFactory factory = new DiskFileItemFactory(1024 * 20, tempDir);
		ServletFileUpload sfu = new ServletFileUpload(factory);
		//限制图片文件大小
		sfu.setFileSizeMax(1024 * 50);
		try {
			Book book = new Book();
			List<FileItem> fileItemList = sfu.parseRequest(request);
			// 循环遍历表单提交的信息：其中只有image是特殊的
			for (FileItem fileItem : fileItemList) {
				// 获取表单项名称bname，price等
				String fieldName = fileItem.getFieldName();
				if (fieldName.equals("bname")) {
					// 以String格式获取表单项内容，并设置编码为UTF-8！
					String bname = fileItem.getString("UTF-8");
					book.setBname(bname);
				} else if (fieldName.equals("price")) {
					String price = fileItem.getString("UTF-8");
					// 将String转换成Double类型
					book.setPrice(Double.parseDouble(price));
				} else if (fieldName.equals("author")) {
					String author = fileItem.getString("UTF-8");
					book.setAuthor(author);
				} else if (fieldName.equals("cid")) {
					String cid = fileItem.getString("UTF-8");
					// category中只需要有cid存在就行
					Category category = new Category();
					category.setCid(cid);
					book.setCategory(category);
				} else if (fieldName.equals("image")) {
					// 获得存放图片的文件夹路径
					String savePath = this.getServletContext().getRealPath(
							"/book_img");
					// 获取文件名
					String name = fileItem.getName();
					// 验证文件名的扩展名
					int index = name.lastIndexOf(".");
					if (index < 0) {
						request.setAttribute("categoryList", categoryList);
						request.setAttribute("msg", "你上传的是神马玩意儿，没有扩展名呢！");
						request.getRequestDispatcher(
								"/adminjsps/admin/book/add.jsp").forward(
								request, response);
						return;
					}
					
					// 判断图片格式
					String ext = name.substring(index+1);
					if (!ext.equalsIgnoreCase("jpg") && !ext.equalsIgnoreCase("png") && !ext.equalsIgnoreCase("bmp")) {
						request.setAttribute("categoryList", categoryList);
						request.setAttribute("msg", "上传的图片只能是：jpg、png、bmp的");
						request.getRequestDispatcher(
								"/adminjsps/admin/book/add.jsp").forward(
								request, response);
						return;
					}
					// 判断是否是图片格式
					String contentType = fileItem.getContentType();
					if (!contentType.startsWith("image/")) {
						request.setAttribute("categoryList", categoryList);
						request.setAttribute("msg", "你上传的不是图片！");
						request.getRequestDispatcher(
								"/adminjsps/admin/book/add.jsp").forward(
								request, response);
						return;
					}
					
					// 保存文件
					name = CommonUtils.uuid() + "." + ext;
					File file = new File(savePath, name);
					fileItem.write(file);//保存图片
					fileItem.delete();//无论如何都干掉临时文件
					
					/*
					 * 判断图片尺寸
					 */
					Image image = new ImageIcon(file.getAbsolutePath()).getImage();
					if (image.getWidth(null) > 150 || image.getHeight(null) > 150) {
						request.setAttribute("categoryList", categoryList);
						request.setAttribute("msg", "图片的尺寸必须在150*150之内！");
						request.getRequestDispatcher(
								"/adminjsps/admin/book/add.jsp").forward(
								request, response);
						//删除已经保存的不符合规定的图片
						file.delete();
						return;
					}
					// 将图片的相对路径添加到book的属性中
					book.setImage("book_img/" + name);
				}
			}
			//添加图书
			book.setBid(CommonUtils.uuid());
			BookService bookService = new BookService();
			bookService.add(book);
			request.setAttribute("msg", "添加图书成功！");
			request.getRequestDispatcher("/adminjsps/admin/msg.jsp").forward(request, response);
		} catch (Exception e) {
			if (e instanceof FileUploadBase.FileSizeLimitExceededException) {
				request.setAttribute("categoryList", categoryList);
				request.setAttribute("msg", "图片不能超过50KB！");
				request.getRequestDispatcher(
						"/adminjsps/admin/book/add.jsp").forward(
						request, response);
				return;
			}
			throw new ServletException(e);
		}

	}

}
