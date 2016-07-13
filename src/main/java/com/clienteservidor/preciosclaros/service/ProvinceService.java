package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Province;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface ProvinceService extends GenericService<Province> {
	
	Collection<Province> findAll(Integer offset, Integer limit, String query);

	int length (String query);
}
