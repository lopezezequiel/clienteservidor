package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.model.Firm;

public interface FirmDao {
    Firm findById(int id);
    Collection<Firm> findAll(Integer offset, Integer limit, Company company, String query);    
    int length(Company company, String query);
    void persist(Firm firm);
    void update(Firm firm);
    void delete(Firm firm);
}
