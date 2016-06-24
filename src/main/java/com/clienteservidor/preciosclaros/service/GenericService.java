package com.clienteservidor.preciosclaros.service;

public interface GenericService<T> {
	T findById(int id);
    void persist(T entity);     
    void update(int id, T entity);
    void delete(T entity);
}
