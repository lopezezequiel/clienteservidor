package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;
import com.clienteservidor.preciosclaros.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService, UserDetailsService {
	private static final int LIMIT = 1000;

	@Autowired
	private UserDao userDao;
	
	public User findById(int id) {
		return userDao.findById(id);
	}

	public Collection<User> findAll(Integer offset, Integer limit, String username) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
		return userDao.findAll(offset, limit, username);
	}

	public void persist(User user) {
		userDao.persist(user);
		
	}

	public void update(int id, User user) {
		User oldUser = userDao.findById(id);

		if(oldUser == null) {
			//TODO exception
		}

		user.setId(id);
		user.setVersion(oldUser.getVersion());
		MyBeanUtils.copyProperties(user, oldUser);

		userDao.update(oldUser);
		
	}

	public void delete(User user) {
		userDao.delete(user);
	}

	
	
	
	
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user= userDao.findByUserName(username);
		List<GrantedAuthority> authorities = 
                buildUserAuthority(user.getUserRole());

		org.springframework.security.core.userdetails.User user2= buildUserForAuthentication(user, authorities);
		return user2;
		
	}
	
	// Converts com.mkyong.users.model.User user to
	// org.springframework.security.core.userdetails.User
	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, 
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), 
				user.isEnabled(), true, true, true, authorities);
	}

	private List<GrantedAuthority> buildUserAuthority(Set<UserRole> userRoles) {

			Set<GrantedAuthority> setAuths = new HashSet<GrantedAuthority>();

			// Build user's authorities
			for (UserRole userRole : userRoles) {
				setAuths.add(new SimpleGrantedAuthority(userRole.getRole()));
			}

			List<GrantedAuthority> Result = new ArrayList<GrantedAuthority>(setAuths);

			return Result;
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	


		

}
