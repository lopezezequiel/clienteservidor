package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Firm;

public interface BranchDao {
	Branch findById(int id);
	int length(Firm firm, Locality locality);
    Branch persist(Branch branch);
    void update(Branch branch);
    void delete(Branch branch);
	Collection<Branch> findAll(Integer offset, Integer limit, Firm firm, Locality locality, Double latitude,
			Double longitude);
}
