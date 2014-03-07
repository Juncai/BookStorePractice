package jon.bookstore.cart;

import jon.bookstore.book.Book;

public class CartItem {
	private Book book;
	private int count;
	private double subtotal;
	public Book getBook() {
		return book;
	}
	public void setBook(Book book) {
		this.book = book;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public double getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(double subtotal) {
		this.subtotal = subtotal;
	}
	@Override
	public String toString() {
		return "CartItem [book=" + book + ", count=" + count + ", subtotal="
				+ subtotal + "]";
	}
	public CartItem(Book book, int count, double subtotal) {
		super();
		this.book = book;
		this.count = count;
		this.subtotal = subtotal;
	}
	public CartItem() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
