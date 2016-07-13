package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface LocalityService extends GenericService<Locality>{


	public Collection<Locality> findAll(Integer offset, Integer limit, String query,
			Integer departmentId);

	public int length(String query,Integer departmentId);
}
