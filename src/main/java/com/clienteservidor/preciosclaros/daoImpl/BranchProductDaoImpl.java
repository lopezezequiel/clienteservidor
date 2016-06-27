package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.BranchProductDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;

@Repository("branchProductDao")
public class BranchProductDaoImpl extends GenericDaoImpl<BranchProduct> implements BranchProductDao {

	public BranchProductDaoImpl() {
		this.entityClass = BranchProduct.class; 
	}

	public int length(Branch branch, Product product, Boolean expired) {

		return getQuery().filterEq("branch", branch).filterEq("product", product)
				.filterEq("expired", expired).getResults().size();
	}

	@SuppressWarnings("unchecked")
	public Collection<BranchProduct> findAll(Integer offset, Integer limit, Branch branch, Collection<Product> products,
			Boolean expired) {

		return getQuery().setOffset(offset).setLimit(limit)
				.filterEq("branch", branch).filterIn("product", products)
				.filterEq("expired", expired).getResults();
	}

	public int length(Branch branch, Collection<Product> products, Boolean expired) {

		return getQuery().filterEq("branch", branch).filterIn("product", products)
				.filterEq("expired", expired).getResults().size();
	}

}
