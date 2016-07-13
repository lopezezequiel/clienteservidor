package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface CompanyService extends GenericService<Company> {
	

	public Collection<Company> findAll(Integer offset, Integer limit, String query);

	public int length (String query);
}
