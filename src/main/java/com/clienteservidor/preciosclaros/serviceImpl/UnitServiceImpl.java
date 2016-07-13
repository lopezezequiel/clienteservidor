package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.UnitDao;
import com.clienteservidor.preciosclaros.model.Unit;
import com.clienteservidor.preciosclaros.service.UnitService;

@Service("unitService")
@Transactional
public class UnitServiceImpl implements UnitService {
	private static final int LIMIT = 1000;

	@Autowired
	private UnitDao unitDao;

	public Unit findById(int id) {
		return unitDao.findById(id);
	}

	public Unit persist(Unit unit) {
		return unitDao.persist(unit);
	}

	public void update(Unit unit) {
		unitDao.update(unit);
	}

	public void delete(Unit unit) {
	    unitDao.delete(unit);
	}

	public Collection<Unit> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;

	    return unitDao.findAll(offset, limit, query);
	}

}