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

	public void persist(Unit unit) {
		unitDao.persist(unit);
	}

	public void update(int id, Unit unit) {
		Unit oldUnit = unitDao.findById(id);

		if(oldUnit == null) {
			//TODO exception
		}

		unit.setId(id);
		unit.setVersion(oldUnit.getVersion());
		MyBeanUtils.copyProperties(unit, oldUnit);

		unitDao.update(oldUnit);
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