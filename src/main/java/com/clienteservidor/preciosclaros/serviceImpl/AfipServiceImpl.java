package com.clienteservidor.preciosclaros.serviceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.dao.AfipDao;
import com.clienteservidor.preciosclaros.model.Afip;
import com.clienteservidor.preciosclaros.service.AfipService;

@Service("afipService")
@Transactional
public class AfipServiceImpl implements AfipService{
	
	@Autowired
	private AfipDao afipDao;

	public Afip findByCuit(String cuit) {
		return afipDao.findByCuit(cuit);
	}

}
