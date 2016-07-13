package com.clienteservidor.preciosclaros.service;

import java.util.Collection;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface UserService {
	
	@Secured({UserRole.ADMIN_C1, UserRole.ADMIN_C2})
	Collection<User> findAll(Integer offset, Integer limit, String query);

	@Secured({UserRole.ADMIN_C1, UserRole.ADMIN_C2})
	int size(String query);


	@Secured({UserRole.ADMIN_C2})
	User findById(int id);   

	@Secured({UserRole.ADMIN_C2})
    void update(User user);

	@Secured({UserRole.ADMIN_C2})
    void delete(User user);

	User persist(User user);

	void enable(String key);

}
