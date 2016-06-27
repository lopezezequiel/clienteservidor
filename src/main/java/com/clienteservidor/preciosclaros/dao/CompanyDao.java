package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Company;

public interface CompanyDao {
    Company findById(int id);
    Collection<Company> findAll(Integer offset, Integer limit, String name);    
    int length(String name);
    Company persist(Company company);
    void update(Company company);
    void delete(Company company);
}
