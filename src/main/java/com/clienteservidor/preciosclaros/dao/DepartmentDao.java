package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Province;

public interface DepartmentDao {
	Department findById(int id);
	Collection<Department> findAll(Integer offset, Integer limit, Province province, String name);
	int length(String name, Province province);
    Department persist(Department city);
    void update(Department city);
    void delete(Department city);
}