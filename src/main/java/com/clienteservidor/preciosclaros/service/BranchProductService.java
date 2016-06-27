package com.clienteservidor.preciosclaros.service;

import java.util.Collection;
import java.util.List;

import com.clienteservidor.preciosclaros.model.BranchProduct;

public interface BranchProductService extends GenericService<BranchProduct> {

	Collection<BranchProduct> findAll(Integer offset, Integer limit, int branchId, List<String> eans, boolean expired);

	void update(int branchId, String ean, BranchProduct branchProduct);

	void logicalDelete(int branchId, String ean);

	void batchUpdate(int branchId, Collection<BranchProduct> collection);

}
