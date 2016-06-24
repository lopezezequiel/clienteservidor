package com.clienteservidor.preciosclaros.serviceImpl;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.DepartmentDao;
import com.clienteservidor.preciosclaros.dao.ProvinceDao;
import com.clienteservidor.preciosclaros.model.Company;
import com.clienteservidor.preciosclaros.model.Department;
import com.clienteservidor.preciosclaros.model.Firm;
import com.clienteservidor.preciosclaros.model.Province;
import com.clienteservidor.preciosclaros.service.DepartmentService;

@Transactional
@Service ("departmentService")
public class DepartmentServiceImpl implements DepartmentService {
	private static final int LIMIT = 1000;
	
	@Autowired
	private DepartmentDao departmentDao;
	
	@Autowired
	private ProvinceDao provinceDao;

	public Department findById(int id) {
		return departmentDao.findById(id);
	}

	public void persist(Department entity) {
		departmentDao.persist(entity);
	}

	public void update(int id, Department entity) {
		Department oldDepartment = departmentDao.findById(id);
		if(oldDepartment == null) {
			//TODO exception
		}

		entity.setId(id);
		entity.setVersion(oldDepartment.getVersion());
		MyBeanUtils.copyProperties(entity, oldDepartment);

		departmentDao.update(oldDepartment);
	}

	public void delete(Department entity) {
		departmentDao.delete(entity);
	}

	public Collection<Department> findAll(Integer offset, Integer limit, Integer provinceId, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    
	    Province province = null;
	    if(provinceId != null){
	    	province = provinceDao.findById(provinceId);
	    	if (province == null){
	    		//TODO exception
	    	}
	    }
	    return departmentDao.findAll(offset, limit, province, query);
	}

	public int length(Integer provinceId, String query) {
		Province province = null;
	    if(provinceId != null){
	    	province = provinceDao.findById(provinceId);
	    	if (province == null){
	    		//TODO exception
	    	}
	    }
	    return departmentDao.length(query, province);
	}

}
