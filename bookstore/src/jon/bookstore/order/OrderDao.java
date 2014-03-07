package jon.bookstore.order;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import jon.bookstore.book.Book;
import jon.jdbc.utils.JdbcUtils;
import jon.utils.CommonUtils;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class OrderDao {
	private QueryRunner qr = new QueryRunner(JdbcUtils.getDataSource());

	public void updateState(String oid, int state) {
		try {
			String sql = "update orders set state=? where oid=?";
			qr.update(sql, state, oid);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Order> findAll() {

		try {
			/*
			 * 查询uid的所有order
			 */
			// 根据uid查询订单，并以订单时间倒序
			String sql = "select * from orders order by ordertime desc";
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(
					Order.class));
			/*
			 * 循环遍历List<Order>
			 */
			sql = "select * from orderitem oi, book b where oi.bid=b.bid and oi.oid=?";
			for (Order order : orderList) {
				/*
				 * 查询每个order的所有orderItem，即orderItemList
				 * 因为OrderItem与Book关联，所以把book一起查出来，这需要多表查询
				 * 把结果集转发到List<Map>，其中每个Map对应一行记录
				 * 一行记录还是两张表的记录（orderItem + book）
				 */
				/*
				 * 每个Map都是一个OrderItem和一个Book
				 * 循环遍历之，来处理每个OrderItem和Book
				 * 1通过Map生成OrderItem对象
				 * 2通过Map生成Book对象
				 * 3把Book设置给OrderItem
				 * 4把OrderItem添加到当前的Order中
				 */
				List<Map<String, Object>> mapList = qr.query(sql,
						new MapListHandler(),
						order.getOid());
				for (Map<String, Object> map : mapList) {
					OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
					Book book = CommonUtils.toBean(map, Book.class);
					orderItem.setBook(book);
					orderItem.setOrder(order);
					order.addOrderItem(orderItem);
				}
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Order> findByState(int state) {

		try {
			/*
			 * 查询uid的所有order
			 */
			// 根据uid查询订单，并以订单时间倒序
			String sql = "select * from orders where state=? order by ordertime desc";
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(
					Order.class), state);
			/*
			 * 循环遍历List<Order>
			 */
			sql = "select * from orderitem oi, book b where oi.bid=b.bid and oi.oid=?";
			for (Order order : orderList) {
				/*
				 * 查询每个order的所有orderItem，即orderItemList
				 * 因为OrderItem与Book关联，所以把book一起查出来，这需要多表查询
				 * 把结果集转发到List<Map>，其中每个Map对应一行记录
				 * 一行记录还是两张表的记录（orderItem + book）
				 */
				/*
				 * 每个Map都是一个OrderItem和一个Book
				 * 循环遍历之，来处理每个OrderItem和Book
				 * 1通过Map生成OrderItem对象
				 * 2通过Map生成Book对象
				 * 3把Book设置给OrderItem
				 * 4把OrderItem添加到当前的Order中
				 */
				List<Map<String, Object>> mapList = qr.query(sql,
						new MapListHandler(),
						order.getOid());
				for (Map<String, Object> map : mapList) {
					OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
					Book book = CommonUtils.toBean(map, Book.class);
					orderItem.setBook(book);
					orderItem.setOrder(order);
					order.addOrderItem(orderItem);
				}
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public Order findByOid(String oid) {
		
		try {
			String sql = "select * from orders where oid=?";
			Order order = qr.query(sql, new BeanHandler<Order>(Order.class),
					oid);
			
			sql = "select * from orderitem oi,book b where oi.bid=b.bid and oid=?";
			
			List<Map<String, Object>> mapList = qr.query(sql,
					new MapListHandler(),
					order.getOid());
			for (Map<String, Object> map : mapList) {
				OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
				Book book = CommonUtils.toBean(map, Book.class);
				orderItem.setBook(book);
				orderItem.setOrder(order);
				order.addOrderItem(orderItem);
			}
			return order;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}
	
	public List<Order> findByUid(String uid) {

		try {
			/*
			 * 查询uid的所有order
			 */
			// 根据uid查询订单，并以订单时间倒序
			String sql = "select * from orders where uid=? order by ordertime desc";
			List<Order> orderList = qr.query(sql, new BeanListHandler<Order>(
					Order.class), uid);
			/*
			 * 循环遍历List<Order>
			 */
			sql = "select * from orderitem oi, book b where oi.bid=b.bid and oi.oid=?";
			for (Order order : orderList) {
				/*
				 * 查询每个order的所有orderItem，即orderItemList
				 * 因为OrderItem与Book关联，所以把book一起查出来，这需要多表查询
				 * 把结果集转发到List<Map>，其中每个Map对应一行记录
				 * 一行记录还是两张表的记录（orderItem + book）
				 */
				/*
				 * 每个Map都是一个OrderItem和一个Book
				 * 循环遍历之，来处理每个OrderItem和Book
				 * 1通过Map生成OrderItem对象
				 * 2通过Map生成Book对象
				 * 3把Book设置给OrderItem
				 * 4把OrderItem添加到当前的Order中
				 */
				List<Map<String, Object>> mapList = qr.query(sql,
						new MapListHandler(),
						order.getOid());
				for (Map<String, Object> map : mapList) {
					OrderItem orderItem = CommonUtils.toBean(map, OrderItem.class);
					Book book = CommonUtils.toBean(map, Book.class);
					orderItem.setBook(book);
					orderItem.setOrder(order);
					order.addOrderItem(orderItem);
				}
			}
			return orderList;
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
	}

	public void add(Order order) {
		try {
			// 1把order插入到数据库
			String sql = "insert into orders values(?,?,?,?,?,?)";
			// ordertime是util 的时间类型，不是sql的时间类型，sql的时间类型也不行，应该创建一个时间戳
			// util时间与sql时间用毫秒联系起来（getTime()）！
			Timestamp ts = new Timestamp(order.getOrdertime().getTime());
			qr.update(sql, order.getOid(), order.getTotal(), ts, order
					.getState(), order.getAddress(), order.getUser().getUid());

			// 2获取所有OrderItem遍历之
			// 3插入每个OrderItem
			sql = "insert into orderitem values(?,?,?,?,?)";
			Object[][] params = new Object[order.getOrderItemSet().size()][5];
			// 使用批处理，循环只是为了设置参数
			// 执行一条sql语句需要一个Object[]来做参数
			// 执行多条sql语句需要Object[][]来做参数
			int i = 0;
			for (OrderItem item : order.getOrderItemSet()) {
				params[i++] = new Object[] { item.getItemid(), item.getCount(),
						item.getSubtotal(), item.getBook().getBid(),
						item.getOrder().getOid() };
			}
			qr.batch(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
}
