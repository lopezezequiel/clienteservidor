package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.DepartmentDao;
import com.clienteservidor.preciosclaros.dao.LocalityDao;
import com.clienteservidor.preciosclaros.dao.ProvinceDao;
import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Locality;
import com.clienteservidor.preciosclaros.model.Province;
import com.clienteservidor.preciosclaros.service.LocalityService;

@Transactional
@Service ("localityService")
public class LocalityServiceImpl implements LocalityService {
	private static final int LIMIT = 1000;

	
	@Autowired
	private LocalityDao localityDao;

	@Autowired
	private ProvinceDao provinceDao;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	public Locality findById(int id) {
		return localityDao.findById(id);
	}

	public void persist(Locality t) {
		localityDao.persist(t);
	}

	public void update(int id, Locality locality) {
		Locality oldCity = localityDao.findById(id);

		if(oldCity == null) {
			//TODO exception
		}

		locality.setId(id);
		locality.setVersion(oldCity.getVersion());
		MyBeanUtils.copyProperties(locality, oldCity);

		localityDao.update(oldCity);
	}

	public void delete(Locality locality) {
		localityDao.delete(locality);
	}

	public Collection<Locality> findAll(Integer offset, Integer limit, String query, Integer departmentId) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    
	    Department department = null;
	    if(departmentId != null){
	    	department = departmentDao.findById(departmentId);
	    	if (department == null){
	    		//TODO exception
	    	}
	    }
	    return localityDao.findAll(offset, limit, query, department);
	}

	public int length(String query, Integer departmentId) {
	    Department department = null;
	    if(departmentId != null){
	    	department = departmentDao.findById(departmentId);
	    	if (department == null){
	    		//TODO exception
	    	}
	    }
	    return localityDao.length(query, department);
	}
}
