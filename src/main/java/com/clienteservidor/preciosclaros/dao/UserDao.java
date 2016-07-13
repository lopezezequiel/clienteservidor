package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.User;

public interface UserDao {
	User findById(int id);
	User findByMail(String mail);
	Collection<User> findAll(Integer offset, Integer limit, String query);
    User persist(User user);     
    void update(User user);
    void delete(User user);
	int size(String query);
	User findByName(String name);
	User findByHashCode(String key);
}
