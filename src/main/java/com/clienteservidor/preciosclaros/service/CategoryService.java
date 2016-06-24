package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Category;

public interface CategoryService extends GenericService<Category>{

	Collection<Category> findAll(Integer offset, Integer limit, Integer parentId, String query);
	int length(Integer parentId, String query);

}
