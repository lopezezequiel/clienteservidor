package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Unit;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface UnitService extends GenericService<Unit>{

	Collection<Unit> findAll(Integer offset, Integer limit, String query);
}
