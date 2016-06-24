package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.User;

public interface UserDao {
	User findById(int id);
	User findByUserName(String username);
	Collection<User> findAll(Integer offset, Integer limit, String username);
    void persist(User user);     
    void update(User user);
    void delete(User user);
}
