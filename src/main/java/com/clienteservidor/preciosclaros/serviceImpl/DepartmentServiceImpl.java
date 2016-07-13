package com.clienteservidor.preciosclaros.serviceImpl;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.DepartmentDao;
import com.clienteservidor.preciosclaros.dao.ProvinceDao;
import com.clienteservidor.preciosclaros.model.Department;
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

	public Department persist(Department department) {
		return departmentDao.persist(department);
	}

	public void update(Department department) {
		departmentDao.update(department);
	}

	public void delete(Department department) {
		departmentDao.delete(department);
	}

	public Collection<Department> findAll(Integer offset, Integer limit, Integer provinceId, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
	    

	    Province province = (provinceId == null) ? null : provinceDao.findById(provinceId);
	    return departmentDao.findAll(offset, limit, province, query);
	}

	public int length(Integer provinceId, String query) {

	    Province province = (provinceId == null) ? null : provinceDao.findById(provinceId);
	    return departmentDao.length(query, province);
	}

}
