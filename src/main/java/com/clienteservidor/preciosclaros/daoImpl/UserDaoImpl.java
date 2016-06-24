package com.clienteservidor.preciosclaros.daoImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.Criteria;
import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {
	public UserDaoImpl() {
		this.entityClass = User.class; 
	}

	public Collection<User> findAll(Integer offset, Integer limit, String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		setOffset(criteria, offset);
		setLimit(criteria, limit);
		filterEq(criteria, "username", username);

		return (Collection<User>) criteria.list();
	}

	public User findByUserName(String username) {
		Criteria criteria = getSession().createCriteria(User.class);
		filterEq(criteria, "username", username);
		User user= (User) criteria.list().get(0);
		return user;
		
		//return (User) getSession().get(entityClass, username);
	}

}
