package com.clienteservidor.preciosclaros.daoImpl;

import java.util.Collection;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
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

		@SuppressWarnings("unchecked")
		List<User> users = getQuery().filterEq("mail", mail).getResults();
		
		if(users.size() != 1) {
	        throw new EntityNotFoundException();
		}
		
		return users.get(0);
	}

	public int size(String query) {
		return getQuery().search(query, "mail").getResults().size();
	}

	public User findByName(String name) {
		return (User) getQuery().filterEq("name", name).getResults().get(0);
	}

}
