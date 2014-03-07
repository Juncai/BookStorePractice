package jon.bookstore.order;

import java.util.List;

public class OrderService {
	private OrderDao orderDao = new OrderDao();
	
	public Order findByOid(String oid){
		return orderDao.findByOid(oid);
	}
	
	public List<Order> findByUid(String uid) {
		return orderDao.findByUid(uid);
	}
	
	public void add(Order order) {
		orderDao.add(order);
	}
	
	public List<Order> findAll() {
		return orderDao.findAll();
	}
	
	public List<Order> findByState(int state) {
		return orderDao.findByState(state);
	}
	
	public void updateState(String oid, int state) {
		orderDao.updateState(oid, state);
	}
}
