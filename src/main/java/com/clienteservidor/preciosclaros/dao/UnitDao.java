package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Unit;

public interface UnitDao {
	Unit findById(int id);
	Collection<Unit> findAll(Integer offset, Integer limit, String query);	
    Unit persist(Unit unit);
    void update(Unit unit);
    void delete(Unit unit);

}
