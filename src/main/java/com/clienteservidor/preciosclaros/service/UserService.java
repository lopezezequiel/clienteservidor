package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.User;

public interface UserService extends GenericService<User> {
	Collection<User> findAll(Integer offset, Integer limit, String username);

}
