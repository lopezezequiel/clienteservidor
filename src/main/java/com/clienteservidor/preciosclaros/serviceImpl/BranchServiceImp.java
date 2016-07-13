package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.Exceptions.UnauthorizedException;
import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.dao.BranchProductDao;
import com.clienteservidor.preciosclaros.dao.LocalityDao;
import com.clienteservidor.preciosclaros.dao.ProductDao;
import com.clienteservidor.preciosclaros.dao.FirmDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.BranchProduct;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.Session;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;
import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.service.BranchService;

@Service("branchService")
@Transactional
public class BranchServiceImp implements BranchService {
	private static final int LIMIT = 1000;
	
	@Autowired
	private BranchDao branchDao;
	
	@Autowired
	private BranchProductDao branchProductDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private LocalityDao localityDao;
	
	@Autowired
	private Session session;
	
	@Autowired
	private FirmDao firmDao;

	public Branch findById(int id) {
		return branchDao.findById(id);
	}

	public Collection<Branch> findAll(Integer offset, Integer limit,
			Integer firmId,  List<Integer> ids, Integer localityId, Double latitude, Double longitude) {

		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    Firm firm = (firmId == null) ? null : firmDao.findById(firmId);
	    Locality locality = (localityId == null) ? null : localityDao.findById(localityId);
		Collection<Product> products = (ids == null) ? null : findProductsById(ids);

		return branchDao.findAll(offset, limit, products, firm, locality, latitude, longitude);
	}

	public Branch persist(Branch branch) {		
		return branchDao.persist(branch);
	}

	public void update(Branch branch) {
		branchDao.update(branch);
	}

	public void delete(Branch branch) {
		branchDao.delete(branch);
	}

	public int length(Integer firmId, Integer localityId, List<Integer> ids) {
		Collection<Product> products = (ids == null) ? null : findProductsById(ids);
	    Firm firm = (firmId == null) ? null : firmDao.findById(firmId);
	    Locality locality = (localityId == null) ? null : localityDao.findById(localityId);
	    
	    return branchDao.length(firm, locality, products);
	}

	@Override
	public Collection<Product> findAllProducts(Integer offset, Integer limit, int branchId, Collection<Integer> ids,
			boolean expired) {
		
		Branch branch = branchDao.findById(branchId);
		Collection<Product> products = (ids == null) ? null : findProductsById(ids);
	   	Collection<BranchProduct> branchProductList = branchProductDao
	   			.findAll(offset, limit, branch, products, expired);
	   	
	   	return getProducts(branchProductList);
	}
	
	
	private Collection<Product> findProductsById(Collection<Integer> ids) {
	    List<Product> products = new ArrayList<Product>();
	    for(Integer id: ids) {
	    	products.add(productDao.findById(id));
	    }
	    
	    return products;
	}

	private Collection<Product> getProducts(Collection<BranchProduct> branchProductList) {
		Collection<Product> products = new ArrayList<Product>();

	   	for(BranchProduct branchProduct: branchProductList) {
	   		Product product = branchProduct.getProduct();
	   		product.setPrice(branchProduct.getPrice());
	   		product.setPromotions(branchProduct.getPromotions());
	   		products.add(product);
	   	}
	   	
	   	return products;
	}

	@Override
	public void batchUpdateProducts(int branchId, Collection<BranchProduct> collection) {
		Branch branch = branchDao.findById(branchId);
		
		if(session.getUser() != branch.getAdmin()) {
			throw new UnauthorizedException();
		}

		for(BranchProduct bp: collection) {

			logicalDelete(branch, bp.getProduct());

			bp.setBranch(branch);
			bp.setExpired(false);
			bp.setVersion(0);
			
			branchProductDao.persist(bp);
		}
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
	


	public void logicalDelete(int branchId, String ean) {
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findByEAN(ean);

		logicalDelete(branch, product);
	}

	public void update(Branch branch, Product product, BranchProduct branchProduct) {

		logicalDelete(branch, product);

		branchProduct.setBranch(branch);
		branchProduct.setExpired(false);
		branchProduct.setProduct(product);
		branchProduct.setVersion(0);
		
		branchProductDao.persist(branchProduct);
	}

	@Override
	public void updateProducts(int branchId, String ean, BranchProduct branchProduct) {
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findByEAN(ean);

		update(branch, product, branchProduct);	
	}

	@Override
	public Product findProductById(int branchId, int productId) {
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findById(productId);
		BranchProduct branchProduct =  branchProductDao.findProduct(branch, product);
		product = branchProduct.getProduct();
   		product.setPrice(branchProduct.getPrice());
   		product.setPromotions(branchProduct.getPromotions());
		return product;
	}

	@Override
	public void updateBranchProduct(int branchId, BranchProduct branchProduct) {
		User user = session.getUser();
		Branch branch = branchDao.findById(branchId);

		//check admin
		if(branch.getAdmin().getId() != user.getId()) {
			throw new UnauthorizedException();
		}

		logicalDelete(branch, branchProduct.getProduct());

		branchProduct.setBranch(branch);
		branchProduct.setExpired(false);
		branchProduct.setVersion(0);
		
		branchProductDao.persist(branchProduct);		

	}

	@Override
	public void deleteBranchProduct(int branchId, String ean) {
		User user = session.getUser();
		Branch branch = branchDao.findById(branchId);
		Product product = productDao.findByEAN(ean);


		//check admin
		if(branch.getAdmin().getId() != user.getId()) {
			throw new UnauthorizedException();
		}
				
		logicalDelete(branch, product);
	}
}
