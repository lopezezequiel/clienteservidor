package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;
import java.util.List;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.Firm;

public interface BranchDao {
	Branch findById(int id);
    Branch persist(Branch branch);
    void update(Branch branch);
    void delete(Branch branch);
	Collection<Branch> findAll(Integer offset, Integer limit, Collection<Product> products, Firm firm, Locality locality,
			Double latitude, Double longitude);
	int length(Firm firm, Locality locality, Collection<Product> products);
}
