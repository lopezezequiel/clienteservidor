package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;

@Repository("userDao")
public class UserDaoImpl extends GenericDaoImpl<User> implements UserDao {

	public UserDaoImpl() {
		this.entityClass = User.class; 
	}

	@SuppressWarnings("unchecked")
	public Collection<User> findAll(Integer offset, Integer limit, String query) {
		return getQuery().setLimit(limit).setOffset(offset)
				.search(query, "mail").getResults();
	}

	public User findByMail(String mail) {
		return (User) getQuery().filterEq("mail", mail).getResults().get(0);
	}

	public int size(String query) {
		return getQuery().search(query, "mail").getResults().size();
	}

}
