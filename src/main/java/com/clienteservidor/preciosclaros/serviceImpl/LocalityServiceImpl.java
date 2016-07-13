package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.DepartmentDao;
import com.clienteservidor.preciosclaros.dao.LocalityDao;
import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.service.LocalityService;

@Transactional
@Service ("localityService")
public class LocalityServiceImpl implements LocalityService {
	private static final int LIMIT = 1000;

	
	@Autowired
	private LocalityDao localityDao;

	
	@Autowired
	private DepartmentDao departmentDao;
	
	public Locality findById(int id) {
		return localityDao.findById(id);
	}

	public Locality persist(Locality t) {
		return localityDao.persist(t);
	}

	public void update(Locality locality) {
		localityDao.update(locality);
	}

	public void delete(Locality locality) {
		localityDao.delete(locality);
	}

	public Collection<Locality> findAll(Integer offset, Integer limit, String query, Integer departmentId) throws EntityNotFoundException {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;

	    Department department = (departmentId == null) ? null : departmentDao.findById(departmentId);
	    return localityDao.findAll(offset, limit, query, department);
	}

	public int length(String query, Integer departmentId) {

	    Department department = (departmentId == null) ? null : departmentDao.findById(departmentId);
	    return localityDao.length(query, department);
	}
}
