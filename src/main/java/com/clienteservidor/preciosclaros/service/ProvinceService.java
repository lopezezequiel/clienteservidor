package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Province;

public interface ProvinceService extends GenericService<Province> {
	Collection<Province> findAll(Integer offset, Integer limit, String query);
	int length (String query);
}
