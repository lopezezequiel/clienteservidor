package com.clienteservidor.preciosclaros.service;

import java.util.Collection;
import java.util.List;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface BranchService extends GenericService<Branch> {

	Branch findById(int id);
	
	@Secured({UserRole.ADMIN_C1})
	Branch persist(Branch branch);
	
	@Secured({UserRole.ADMIN_C1})
    void update(Branch branch);
	
	@Secured({UserRole.ADMIN_C1})
    void delete(Branch branch);

	public Collection<Branch> findAll(Integer offset, Integer limit, Integer firmId, List<Integer> ids,
			Integer localityId, Double latitude, Double longitude);

	public void batchUpdateProducts(int branchId, Collection<BranchProduct> collection);
	public void updateProducts(int branchId, String ean, BranchProduct branchProduct);
	public void logicalDelete(int branchId, String ean);
	public int length(Integer firmId, Integer localityId, List<Integer> list);
	Collection<Product> findAllProducts(Integer offset, Integer limit, int branchId, Collection<Integer> ids,
			boolean expired);

	public Product findProductById(int branchId, int productId);


	@Secured({UserRole.BRANCH})
	void updateBranchProduct(int branchId, BranchProduct branchProduct);


	@Secured({UserRole.BRANCH})
	void deleteBranchProduct(int branchId, String ean);
}