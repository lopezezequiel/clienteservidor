package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.CompanyDao;
import com.clienteservidor.preciosclaros.dao.FirmDao;
import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.service.FirmService;

@Transactional
@Service ("firmService")
public class FirmServiceImpl implements FirmService {
	private static final int LIMIT = 1000;

	@Autowired
	private FirmDao firmDao;

	@Autowired
	private CompanyDao companyDao;
	
	public Firm findById(int id) {
		return firmDao.findById(id);
	}

	public void persist(Firm firm) {
		firmDao.persist(firm);
	}

	public void update(int id, Firm firm) {
		Firm oldFirm = firmDao.findById(id);

		if(oldFirm == null) {
			//TODO exception
		}

		firm.setId(id);
		firm.setVersion(oldFirm.getVersion());
		MyBeanUtils.copyProperties(firm, oldFirm);

		firmDao.update(oldFirm);
	}

	public void delete(Firm firm) {
		firmDao.delete(firm);
	}

	public Collection<Firm> findAll(Integer offset, Integer limit, Integer companyId, String query) {
		// TODO Auto-generated method stub
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    
	    Company company = null;
	    if(companyId != null){
	    	company = companyDao.findById(companyId);
	    	if (company == null){
	    		//TODO exception
	    	}
	    }
	    return firmDao.findAll(offset, limit, company, query);
	}

	public int length(Integer companyId, String query) {
		Company company = null;
	    if(companyId != null){
	    	company = companyDao.findById(companyId);
	    	if (company == null){
	    		//TODO exception
	    	}
	    }
	    return firmDao.length(company, query);
	}
}