package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Branch;

public interface BranchService extends GenericService<Branch> {
	public int length(Integer firmId, Integer cityId);
	public Collection<Branch> findAll(Integer offset, Integer limit, Integer firmId, Integer localityId,
			Double latitude, Double longitude);
}