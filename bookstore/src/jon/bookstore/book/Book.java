package jon.bookstore.book;

import jon.bookstore.category.Category;

public class Book {
	private String bid;
	private String bname;
	private Double price;
	private String author;
	private String image;
	private Category category;
	public String getBid() {
		return bid;
	}
	public void setBid(String bid) {
		this.bid = bid;
	}
	public String getBname() {
		return bname;
	}
	public void setBname(String bname) {
		this.bname = bname;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	@Override
	public String toString() {
		return "Book [bid=" + bid + ", bname=" + bname + ", price=" + price
				+ ", author=" + author + ", image=" + image + ", category="
				+ category + "]";
	}
	public Book(String bid, String bname, Double price, String author,
			String image, Category category) {
		super();
		this.bid = bid;
		this.bname = bname;
		this.price = price;
		this.author = author;
		this.image = image;
		this.category = category;
	}
	public Book() {
		super();
		// TODO Auto-generated constructor stub
	}
}
