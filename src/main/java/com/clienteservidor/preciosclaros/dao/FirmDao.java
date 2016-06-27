package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.model.Company;

public interface FirmDao {
    Firm findById(int id);
    Collection<Firm> findAll(Integer offset, Integer limit, Company company, String query);    
    int length(Company company, String query);
    Firm persist(Firm firm);
    void update(Firm firm);
    void delete(Firm firm);
}
