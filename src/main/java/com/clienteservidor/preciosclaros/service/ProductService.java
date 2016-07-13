package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface ProductService extends GenericService<Product> {
	

	Collection<Product> findAll(Integer offset, Integer limit, String query, Integer category);

	int length(String query, Integer category);
}
