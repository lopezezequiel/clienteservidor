package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.Criteria;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.jpa.FullTextQuery;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.beanutils.Query;
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

}
