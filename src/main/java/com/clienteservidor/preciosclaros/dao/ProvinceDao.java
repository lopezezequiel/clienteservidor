package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Province;

public interface ProvinceDao {
	Province findById(int id);
	Collection<Province> findAll(Integer offset, Integer limit, String query);
	int length(String query);
    Province persist(Province province);     
    void update(Province province);
    void delete(Province province);
}
