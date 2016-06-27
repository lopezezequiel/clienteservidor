package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.beanutils.MyBeanUtils;
import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;
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

	public Collection<User> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
		return userDao.findAll(offset, limit, query);
	}

	public User persist(User user) {
		return userDao.persist(user);
	}

	public void update(int id, User user) {
		User oldUser = userDao.findById(id);
		user.setId(id);
		user.setVersion(oldUser.getVersion());
		MyBeanUtils.copyProperties(user, oldUser);

		userDao.update(oldUser);
		
	}

	public void delete(User user) {
		userDao.delete(user);
	}
	
	public UserDetails loadUserByUsername(String mail) {
		User user = userDao.findByMail(mail);
		List<GrantedAuthority> authorities = buildUserAuthority(user.getUserRoles());
		return buildUserForAuthentication(user, authorities);	
	}

	private List<GrantedAuthority> buildUserAuthority(Collection<String> roles) {

			ArrayList<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

			for (String role: roles) {
				authorities.add(new SimpleGrantedAuthority(role));
			}

			return authorities;
	}

	private org.springframework.security.core.userdetails.User buildUserForAuthentication(User user, 
			List<GrantedAuthority> authorities) {
		return new org.springframework.security.core.userdetails.User(
				user.getMail(), user.getPassword(),user.isEnabled(), true,
				true, true, authorities);
	}

	public int size(String query) {
		return userDao.size(query);
	}
	
	public void sendMail(String from, String subject, String message) {
		SimpleMailMessage mailMessage = new SimpleMailMessage();
		mailMessage.setFrom(from);
		mailMessage.setSubject(subject);
		mailMessage.setText(message);
		MailSender mailSender = new JavaMailSenderImpl();
		mailSender.send(mailMessage);
	}

	public User persistRegularUser(User user) {
		user.setUserRoles(new HashSet<String>(Arrays.asList("ROLE_REGULAR")));
		user.setEnabled(false);
		return persist(user);
	}

	@Secured({"ROLE_ROOT"})
	public User persistAdminUser(User user) {
		user.setUserRoles(new HashSet<String>(Arrays.asList("ROLE_ADMIN")));
		user.setEnabled(false);
		return persist(user);
	}

	@Secured({"ROLE_ROOT"})
	public User persistRootUser(User user) {
		user.setUserRoles(new HashSet<String>(Arrays.asList("ROLE_ROOT")));
		user.setEnabled(false);
		return persist(user);
	}

	@Secured({"ROLE_ROOT", "ROLE_ADMIN"})
	public User persistBranchUser(User user) {
		user.setUserRoles(new HashSet<String>(Arrays.asList("ROLE_BRANCH")));
		user.setEnabled(false);
		return persist(user);
	}
}
