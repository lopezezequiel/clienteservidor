package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.model.Product;

public interface ProductDao {
	Product findById(int id);
	Product findByEAN(String ean);
	Collection<Product> findAll(Integer offset, Integer limit, String query, Category category);	
	int length(String query, Category category);
    Product persist(Product product);
    void update(Product product);
    void delete(Product product);
}
