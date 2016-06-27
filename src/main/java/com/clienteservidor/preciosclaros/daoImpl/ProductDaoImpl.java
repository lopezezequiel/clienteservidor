package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.dao.ProductDao;
import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.model.Product;

@Repository("productDao")
public class ProductDaoImpl extends GenericDaoImpl<Product> implements ProductDao {

	public ProductDaoImpl() {
		this.entityClass = Product.class;
	}

	@SuppressWarnings("unchecked")
	public Collection<Product> findAll(Integer offset, Integer limit,
			String query, Category category) {
		
		return (Collection<Product>) getQuery().setOffset(offset).setLimit(limit)
		.filterEq("category", category).search(query, "brand", "description")
		.getResults();
	}

	public int length(String query, Category category) {
		return getQuery().filterEq("category", category)
				.search(query, "brand", "description").getResults().size();
	}

	@SuppressWarnings("unchecked")
	public Product findByEAN(String ean) {
		
		List<Product> products = getQuery().filterEq("ean13", ean).getResults();
		
		if(products.isEmpty()) {
			throw new EntityNotFoundException();
		}

		return products.get(0);
	}

}
