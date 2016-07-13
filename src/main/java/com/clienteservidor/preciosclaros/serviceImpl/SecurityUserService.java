package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
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

import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.Session;
import com.clienteservidor.preciosclaros.model.User;

@Service("securityUserService")
@Transactional
public class SecurityUserService implements UserDetailsService {


	@Autowired
	private UserDao userDao;
	
	@Autowired
	private Session session;
	

	public UserDetails loadUserByUsername(final String mail) throws UsernameNotFoundException {

		try {
			User user = userDao.findByMail(mail);
			session.setUser(user);
			List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
			return buildUserForAuthentication(user, authorities);	
		} catch(Exception e) {
			throw new UsernameNotFoundException(mail);
		}
	}

	private List<GrantedAuthority> buildUserAuthority(Set<String> roles) {

			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

			for (String role: roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			return authorities;
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, 
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getMail(), user.getPassword(), true, true,
				true, true, authorities);
	}

}
