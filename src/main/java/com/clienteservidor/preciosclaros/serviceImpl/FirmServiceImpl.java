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

	public Firm persist(Firm firm) {
		return firmDao.persist(firm);
	}

	public void update(Firm firm) {
		firmDao.update(firm);
	}

	public void delete(Firm firm) {
		firmDao.delete(firm);
	}

	public Collection<Firm> findAll(Integer offset, Integer limit, Integer companyId, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    

	    Company company = (companyId == null) ? null : companyDao.findById(companyId);
	    return firmDao.findAll(offset, limit, company, query);
	}

	public int length(Integer companyId, String query) {

	    Company company = (companyId == null) ? null : companyDao.findById(companyId);
	    return firmDao.length(company, query);
	}
}
