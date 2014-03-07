package jon.bookstore.category;

import java.util.List;

public class CategoryService {
	private CategoryDao categoryDao = new CategoryDao();
	
	public List<Category> all(){
		return categoryDao.all();
	}
	
	public void add(Category category) throws CategoryException {
		/*
		 * 校验该分类是否已经存在
		 */
		Category _category = categoryDao.findByCname(category.getCname());
		if (_category != null) {
			throw new CategoryException("该分类已经存在！");
		}
		categoryDao.add(category);
	}
	
	public void mod(Category category) {
		categoryDao.mod(category);
	}
	
	public void del(String cid) throws CategoryException{
		/*
		 * 需要验证该分类下是否有图书，没有图书才能删除！！！
		 */
		int count = categoryDao.count(cid);
		if (count > 0) {
			throw new CategoryException("删除失败：该分类下还有图书，不能删除！");
		}
		categoryDao.del(cid);
	}

	public Category findByCid(String cid) {
		return categoryDao.findByCid(cid);
	}
}
