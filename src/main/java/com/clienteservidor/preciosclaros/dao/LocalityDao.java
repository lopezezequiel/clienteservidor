package com.clienteservidor.preciosclaros.dao;


import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Locality;

public interface LocalityDao {
	Locality findById(int id);
	Collection<Locality> findAll(Integer offset, Integer limit, String name,Department department);
	int length(String name, Department department);
    Locality persist(Locality locality);
    void update(Locality locality);
    void delete(Locality locality);
}
