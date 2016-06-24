package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Product;

public interface ProductService extends GenericService<Product> {
	Collection<Product> findAll(Integer offset, Integer limit, String query, Integer category);
	int length(String query, Integer category);
}
