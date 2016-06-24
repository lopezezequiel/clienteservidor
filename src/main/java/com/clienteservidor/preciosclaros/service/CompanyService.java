package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Company;

public interface CompanyService extends GenericService<Company>{	
	public Collection<Company> findAll(Integer offset, Integer limit, String query);
	public int length (String query);
}
