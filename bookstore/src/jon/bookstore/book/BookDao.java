package jon.bookstore.book;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import jon.bookstore.category.Category;
import jon.jdbc.utils.JdbcUtils;
import jon.utils.CommonUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class BookDao {
	private QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public List<Book> all() {
		String sql = "select * from book b,category c where b.cid=c.cid and del=false";
		try {
			List<Book> bookList = new ArrayList<Book>();
			List<Map<String, Object>> mapList = qr.query(sql,
					new MapListHandler());
			for (Map<String, Object> map : mapList) {
				Book book = CommonUtils.toBean(map, Book.class);
				Category category = CommonUtils.toBean(map, Category.class);
				book.setCategory(category);
				bookList.add(book);
			}
			return bookList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public List<Book> query(String cid) {
		String sql = "select * from book b,category c where b.cid=? and b.cid=c.cid and del=false";
		try {
			List<Book> bookList = new ArrayList<Book>();
			List<Map<String, Object>> mapList = qr.query(sql,
					new MapListHandler(), cid);
			for (Map<String, Object> map : mapList) {
				Book book = CommonUtils.toBean(map, Book.class);
				Category category = CommonUtils.toBean(map, Category.class);
				book.setCategory(category);
				bookList.add(book);
			}
			return bookList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public Book load(String bid) {
		String sql = "select * from book b,category c where bid=? and b.cid=c.cid and del=false";
		try {
			Map<String, Object> map = qr.query(sql, new MapHandler(), bid);
			Book book = CommonUtils.toBean(map, Book.class);
			Category category = CommonUtils.toBean(map, Category.class);
			book.setCategory(category);
			return book;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	/*
	 * private String bname; private Double price; private String author;
	 * private String image; private Category category;
	 */
	public void mod(Book book) {
		String sql = "update book set bname=?,price=?,author=?,cid=? where bid=?";
		try {
			qr.update(sql, book.getBname(), book.getPrice(), book.getAuthor(),
					book.getCategory().getCid(), book.getBid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void del(String bid) {
		String sql = "update book set del=TRUE where bid=?";
		try {
			qr.update(sql, bid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public void add(Book book) {
		String sql = "insert into book values(?,?,?,?,?,?,FALSE)";
		try {
			qr.update(sql, book.getBid(), book.getBname(), book.getPrice(), book.getAuthor(),
					book.getImage(), book.getCategory().getCid());
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
