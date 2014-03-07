package jon.bookstore.admin.adminuser;

import java.sql.SQLException;

import jon.jdbc.utils.JdbcUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;

public class AdminUserDao {
	private QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public AdminUser login(String username, String password) {
		try {
			String sql = "select * from adminuser where username=? and password=?";
			return qr.query(sql, new BeanHandler<AdminUser>(
					AdminUser.class), username, password);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
}
