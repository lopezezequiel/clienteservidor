package com.clienteservidor.preciosclaros.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.dao.AfipDao;
import com.clienteservidor.preciosclaros.model.Afip;

@Repository("afipDao")
public class AfipDaoImpl implements AfipDao{

	 
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession() {
    	return sessionFactory.getCurrentSession();
    }
    
	public Afip findByCuit(String cuit) {
		Afip afip = (Afip) getSession().get(Afip.class, cuit);

		if(afip == null) {
			throw new EntityNotFoundException();
		}

		return  afip;
	}

}
