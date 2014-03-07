package jon.bookstore.cart;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
	private Map<String, CartItem> map = new LinkedHashMap<String, CartItem>();
	private double total;
	
	
	
	public double getTotal() {
		return total;
	}

	public void setTotal(double total) {
		this.total = total;
	}

	public void add(CartItem item) {
		double subtotal = item.getSubtotal();
		String bid = item.getBook().getBid();
		CartItem _item = map.get(bid);
		if (_item != null) {
			_item.setCount(_item.getCount() + item.getCount());
			_item.setSubtotal(_item.getSubtotal() + subtotal);
			map.put(bid, _item);
		} else {
			map.put(bid, item);			
		}
		this.total += subtotal;
	}
	
	public void del(String bid) {
		CartItem item = map.remove(bid);
		this.total -= item.getSubtotal();
	}
	
	public void clear() {
		map.clear();
		total = 0;
	}
	
	public Collection<CartItem> getCartItems() {
		return map.values();
	}
}
