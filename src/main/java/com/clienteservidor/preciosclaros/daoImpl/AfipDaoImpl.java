package com.clienteservidor.preciosclaros.daoImpl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.AfipDao;
import com.clienteservidor.preciosclaros.model.Afip;

@Repository("afipDao")
public class AfipDaoImpl implements AfipDao{

	 
    @Autowired
    private SessionFactory sessionFactory;
 
    protected Session getSession() {
    	return sessionFactory.getCurrentSession();
    }
    
	public boolean existe(String cuit) {
		return getSession().get(Afip.class, cuit) != null;
	}

}
