package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.dao.BranchProductDao;
import com.clienteservidor.preciosclaros.dao.ProductDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.service.BranchProductService;

@Service("branchProductService")
@Transactional
public class BranchProductServiceImpl implements BranchProductService {


	@Autowired
	private BranchProductDao branchProductDao;

	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private ProductDao productDao;

	public BranchProduct findById(int id) {
		return branchProductDao.findById(id);
	}

	public BranchProduct persist(BranchProduct branchProduct) {
		return branchProductDao.persist(branchProduct);
	}

	public void update(int id, BranchProduct branchProduct) {
		BranchProduct oldBranchProduct = branchProductDao.findById(id);

		branchProduct.setId(id);
		branchProduct.setVersion(oldBranchProduct.getVersion());
		MyBeanUtils.copyProperties(branchProduct, oldBranchProduct);

		branchProductDao.update(oldBranchProduct);
	}

	public void delete(BranchProduct branchProduct) {
		branchProductDao.delete(branchProduct);
	}
	
	private Collection<Product> getProducts(List<String> eans) {
		Collection<Product> products = null;
		if(eans != null) {
			products = new ArrayList<Product>();
			for(String ean: eans) {
				products.add(productDao.findByEAN(ean));
			}
		}
		return products;
	}

	public Collection<BranchProduct> findAll(Integer offset, Integer limit, int branchId, List<String> eans, boolean expired) {
		Branch branch = branchDao.findById(branchId);
		return branchProductDao.findAll(offset, limit, branch, getProducts(eans), expired);
	}

	public void update(int branchId, String ean, BranchProduct branchProduct) {
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findByEAN(ean);

		update(branch, product, branchProduct);
	}

	public void update(Branch branch, Product product, BranchProduct branchProduct) {

		logicalDelete(branch, product);

		branchProduct.setBranch(branch);
		branchProduct.setExpired(false);
		branchProduct.setProduct(product);
		branchProduct.setVersion(0);
		
		branchProductDao.persist(branchProduct);
	}

	public void logicalDelete(int branchId, String ean) {
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findByEAN(ean);

		logicalDelete(branch, product);
	}

	public void logicalDelete(Branch branch, Product product) {

		List<Product> products = new ArrayList<Product>();
		products.add(product);

		Collection<BranchProduct> bps = branchProductDao.findAll(null, null, branch, products, false);

		for(BranchProduct bp: bps) {
			bp.setExpired(true);
			branchProductDao.update(bp);
		}		
	}

	public void batchUpdate(int branchId, Collection<BranchProduct> collection) {
		Branch branch = branchDao.findById(branchId);

		for(BranchProduct bp: collection) {

			logicalDelete(branch, bp.getProduct());

			bp.setBranch(branch);
			bp.setExpired(false);
			bp.setVersion(0);
			
			branchProductDao.persist(bp);
		}
		
	}

}
