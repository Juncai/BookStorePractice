package jon.bookstore.admin.adminuser;

public class AdminUser {
	private String uid;
	private String username;
	private String password;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "AdminUser [uid=" + uid + ", username=" + username
				+ ", password=" + password + "]";
	}
	public AdminUser(String uid, String username, String password) {
		super();
		this.uid = uid;
		this.username = username;
		this.password = password;
	}
	public AdminUser() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
}
