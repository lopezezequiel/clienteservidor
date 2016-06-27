package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Category;

public interface CategoryDao {

	Category findById(int id);
	Collection<Category> findAll(Integer offset, Integer limit, Category parent, String query);
	int length(Category parent, String query);
    Category persist(Category category);     
    void update(Category category);
    void delete(Category category);
}
