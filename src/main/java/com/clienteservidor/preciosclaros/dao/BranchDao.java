package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Firm;

public interface BranchDao {
	Branch findById(int id);
	Collection<Branch> findAll(Integer offset, Integer limit, Firm firm, Locality locality);
	int length(Firm firm, Locality locality);
    void persist(Branch branch);
    void update(Branch branch);
    void delete(Branch branch);
}
