package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.AfipDao;
import com.clienteservidor.preciosclaros.dao.CompanyDao;
import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.service.CompanyService;

@Transactional
@Service ("companyService")
public class CompanyServiceImpl implements CompanyService {
	private static final int LIMIT = 1000;
	
	@Autowired
	private CompanyDao companyDao;
	
	@Autowired
	private AfipDao afipDao;
	
	public Company findById(int id) {
		return companyDao.findById(id);
	}

	public Company persist(Company company) {
		afipDao.findByCuit(company.getCuit());
		return companyDao.persist(company);
	}

	public void update(Company company) {
		afipDao.findByCuit(company.getCuit());
		companyDao.update(company);
	}

	public void delete(Company company) {
		companyDao.delete(company);
	}

	public Collection<Company> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    return companyDao.findAll(offset, limit, query);
	}

	public int length(String query) {
		return companyDao.length(query);
	}
}
