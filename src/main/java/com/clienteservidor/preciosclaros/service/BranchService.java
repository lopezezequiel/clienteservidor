package com.clienteservidor.preciosclaros.service;

import java.util.Collection;
import java.util.List;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;

public interface BranchService extends GenericService<Branch> {
	public Collection<Branch> findAll(Integer offset, Integer limit, Integer firmId, List<Integer> ids,
			Integer localityId, Double latitude, Double longitude);

	public void batchUpdateProducts(int branchId, Collection<BranchProduct> collection);
	public void updateProducts(int branchId, String ean, BranchProduct branchProduct);
	public void logicalDelete(int branchId, String ean);
	public int length(Integer firmId, Integer localityId, List<Integer> list);
	Collection<Product> findAllProducts(Integer offset, Integer limit, int branchId, Collection<Integer> ids,
			boolean expired);

	public Product findProductById(int branchId, int productId);
}