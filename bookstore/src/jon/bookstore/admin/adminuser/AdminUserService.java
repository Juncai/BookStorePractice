package jon.bookstore.admin.adminuser;

public class AdminUserService {
	private AdminUserDao adminUserDao = new AdminUserDao();
	
	public AdminUser login(String username, String password) {
		return adminUserDao.login(username, password);
	}
}
