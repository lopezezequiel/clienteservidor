package com.clienteservidor.preciosclaros.dao;

import java.util.Collection;
import java.util.List;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;

public interface BranchProductDao {
    BranchProduct findById(int id); 
    int length(Branch branch, Collection<Product> products, Boolean expired);
    BranchProduct persist(BranchProduct branchProduct);
    void update(BranchProduct branchProduct);
    void delete(BranchProduct branchProduct);
	Collection<BranchProduct> findAll(Integer offset, Integer limit, Branch branch, Collection<Product> products, Boolean expired);
}
