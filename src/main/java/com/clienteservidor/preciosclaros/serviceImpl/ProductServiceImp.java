package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.CategoryDao;
import com.clienteservidor.preciosclaros.dao.ProductDao;
import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.service.ProductService;

@Transactional
@Service("productService")
public class ProductServiceImp implements ProductService {
	
	private static final int LIMIT = 1000;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private CategoryDao categoryDao;
	
	public Product findById(int id) {
		return productDao.findById(id);
	}

	public Collection<Product> findAll(Integer offset, Integer limit, String query,
			Integer categoryId) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    
	    Category category = (categoryId == null) ? null : categoryDao.findById(categoryId);
		return productDao.findAll(offset, limit, query, category);	
	}

	public Product persist(Product product) {
		return productDao.persist(product);
	}

	public void update(Product product) {
		productDao.update(product);
	}

	public void delete(Product product) {
		productDao.delete(product);
	}

	public int length(String query, Integer categoryId) {
	    Category category = (categoryId == null) ? null : categoryDao.findById(categoryId);
		return productDao.length(query, category);
	}
}
