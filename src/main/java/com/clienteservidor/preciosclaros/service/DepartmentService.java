package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Department;

public interface DepartmentService extends GenericService<Department> {
	Collection<Department> findAll(Integer offset, Integer limit, Integer provinceId, String query);
	int length(Integer provinceId, String query);
}