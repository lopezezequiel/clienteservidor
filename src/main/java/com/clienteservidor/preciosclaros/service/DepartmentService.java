package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface DepartmentService extends GenericService<Department> {

	Collection<Department> findAll(Integer offset, Integer limit, Integer provinceId, String query);

	int length(Integer provinceId, String query);
}