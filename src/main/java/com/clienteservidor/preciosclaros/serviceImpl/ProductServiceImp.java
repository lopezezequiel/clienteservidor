package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.query.dsl.QueryBuilder;
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
	    
	    Category category=null;
	    if (categoryId != null){
	    	category = categoryDao.findById(categoryId);	    	
	    	if (category==null){
	    		//TODO no existe categoria
	    	}
	    }
	    	    
		return productDao.findAll(offset, limit, query, category);	
	}

	public void persist(Product product) {
		productDao.persist(product);
	}

	public void update(int id,Product product) {
		Product oldProduct = productDao.findById(id);

		if(oldProduct == null) {
			//TODO exception
		}
		product.setId(id);
		product.setVersion(oldProduct.getVersion());
		MyBeanUtils.copyProperties(product, oldProduct);

		productDao.update(oldProduct);
	}

	public void delete(Product product) {
		productDao.delete(product);
	}

	public int length(String query, Integer categoryId) {
	    Category category=null;
	    if (categoryId!=null){
	    	category= categoryDao.findById(categoryId);	    	
	    	if (category==null){
	    		return 0; //TODO no existe categoria
	    	}
	    }
	    	    
		return productDao.length(query, category);
	}
}
