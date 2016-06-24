package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.dao.LocalityDao;
import com.clienteservidor.preciosclaros.dao.FirmDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.service.BranchService;

@Service("branchService")
@Transactional
public class BranchServiceImp implements BranchService {
	private static final int LIMIT = 1000;
	
	@Autowired
	private BranchDao branchDao;

	@Autowired
	private LocalityDao localityDao;
	
	@Autowired
	private FirmDao firmDao;

	public Branch findById(int id) {
		return branchDao.findById(id);
	}

	public Collection<Branch> findAll(Integer offset, Integer limit, Integer firmId, Integer cityId) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;


	    Firm firm = null;
	    if (firmId != null){
	    	firm = firmDao.findById(firmId);
		    if (firm == null){
		    	//TODO exception
		    }
	    }
	    
	    Locality locality = null;	    
	    if (cityId != null){
	    	locality = localityDao.findById(cityId);
		    if (locality == null){
		    	//TODO exception
		    }
	    }

		return branchDao.findAll(offset, limit, firm, locality);
	}

	public void persist(Branch branch) {		
		branchDao.persist(branch);
	}

	public void update(int id, Branch branch) {
		Branch oldBranch = branchDao.findById(id);

		if(oldBranch == null) {
			//TODO exception
		}

		branch.setId(id);
		branch.setVersion(oldBranch.getVersion());
		MyBeanUtils.copyProperties(branch, oldBranch);

		branchDao.update(oldBranch);
	}

	public void delete(Branch branch) {
		branchDao.delete(branch);
	}

	public int length(Integer firmId, Integer cityId) {
		Firm firm = null;
	    if (firmId != null){
	    	firm = firmDao.findById(firmId);
		    if (firm == null){
		    	//TODO exception
		    }
	    }
	    
	    Locality locality = null;	    
	    if (cityId != null){
	    	locality = localityDao.findById(cityId);
		    if (locality == null){
		    	//TODO exception
		    }
	    }
	    return branchDao.length(firm, locality);

	}
}
