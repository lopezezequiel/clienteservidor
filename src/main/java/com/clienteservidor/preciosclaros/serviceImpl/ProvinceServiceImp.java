package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.ProvinceDao;
import com.clienteservidor.preciosclaros.model.Province;
import com.clienteservidor.preciosclaros.service.ProvinceService;

@Service("provinceService")
@Transactional
public class ProvinceServiceImp implements ProvinceService {
	private static final int LIMIT = 1000;

	@Autowired
	private ProvinceDao provinceDao;

	public Province findById(int id) {
		return provinceDao.findById(id);
	}

	public Province persist(Province province) {
		return provinceDao.persist(province);
	}

	public void update(int id, Province province) {
		Province oldProvince = provinceDao.findById(id);
		province.setId(id);
		province.setVersion(oldProvince.getVersion());
		MyBeanUtils.copyProperties(province, oldProvince);

		provinceDao.update(oldProvince);
	}

	public void delete(Province province) {
	    provinceDao.delete(province);
	}

	public Collection<Province> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;

	    return provinceDao.findAll(offset, limit,query);
	}

	public int length(String query) {
		return provinceDao.length(query);
	}

}