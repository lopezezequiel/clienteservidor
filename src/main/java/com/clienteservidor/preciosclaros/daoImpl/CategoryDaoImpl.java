package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.CategoryDao;
import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.model.Product;

@Repository("categoryDao")
public class CategoryDaoImpl extends GenericDaoImpl<Category> implements CategoryDao{

	public CategoryDaoImpl() {
		this.entityClass = Category.class; 
	}
	
	@SuppressWarnings("unchecked")
	public Collection<Category> findAll(Integer offset, Integer limit, Category parent, String query) {

		return (Collection<Category>) getQuery().setOffset(offset).setLimit(limit)
		.filterEq("parent", parent).search(query, "name")
		.getResults();
	}

	public int length(Category parent, String query) {
		return getQuery().filterEq("parent", parent).search(query, "name")
				.getResults().size();
	}
}
