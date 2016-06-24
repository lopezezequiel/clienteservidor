package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Branch;

public interface BranchService extends GenericService<Branch> {
	public Collection<Branch> findAll(Integer offset, Integer limit, Integer firmId, Integer cityId);
	public int length(Integer firmId, Integer cityId);
}