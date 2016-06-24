package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Firm;

public interface FirmService extends GenericService<Firm>{

	Collection<Firm> findAll(Integer offset, Integer limit, Integer companyId, String query);
	int length(Integer companyId, String query);
}
