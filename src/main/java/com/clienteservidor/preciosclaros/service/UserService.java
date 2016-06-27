package com.clienteservidor.preciosclaros.service;

import java.util.Collection;
import java.util.Set;

import com.clienteservidor.preciosclaros.model.User;

public interface UserService extends GenericService<User> {
	Collection<User> findAll(Integer offset, Integer limit, String query);
	int size(String query);
	User persistRegularUser(User user);
	void sendMail(String string, String string2, String string3);
	User persistAdminUser(User user);
	User persistRootUser(User user);
	User persistBranchUser(User user);
}
