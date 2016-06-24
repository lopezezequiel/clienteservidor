package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Unit;

public interface UnitService extends GenericService<Unit>{
	Collection<Unit> findAll(Integer offset, Integer limit, String query);
}
