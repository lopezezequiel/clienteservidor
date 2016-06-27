package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.CategoryDao;
import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.service.CategoryService;

@Transactional
@Service("categoryService")
public class CategoryServiceImpl implements CategoryService{
	private static final int LIMIT = 1000;

	@Autowired
	private CategoryDao categoryDao;
	
	public Category findById(int id) {
		return categoryDao.findById(id);
	}

	public Collection<Category> findAll(Integer offset, Integer limit, Integer parentId, String query) {
	    offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    Category parent = (parentId == null) ? null : categoryDao.findById(parentId);
	    return categoryDao.findAll(offset, limit, parent, query);
	}

	public Category persist(Category category) {
		return categoryDao.persist(category);
	}

	public void update(int id, Category category) {
		Category oldCategory = categoryDao.findById(id);

		category.setId(id);
		category.setVersion(oldCategory.getVersion());
		MyBeanUtils.copyProperties(category, oldCategory);

		categoryDao.update(oldCategory);
	}


	public void delete(Category category) {
		categoryDao.delete(category);
	}

	public int length(Integer parentId, String query) {
		Category parent = (parentId == null) ? null : categoryDao.findById(parentId);
	    return categoryDao.length(parent, query);
	}

}
