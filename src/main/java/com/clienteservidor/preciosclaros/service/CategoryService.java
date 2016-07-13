package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Category;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface CategoryService extends GenericService<Category>{

	Collection<Category> findAll(Integer offset, Integer limit, Integer parentId, String query);

	int length(Integer parentId, String query);

}
