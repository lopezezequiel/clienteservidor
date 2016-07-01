package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;
import com.clienteservidor.preciosclaros.service.UserService;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	private static final int LIMIT = 1000;
	

    
    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;

	public int size(String query) {
		return userDao.size(query);
	}

	public Collection<User> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
		return userDao.findAll(offset, limit, query);
	}

	public User getUser() {
		String username = SecurityContextHolder.getContext().getAuthentication()
				.getName();

		return userDao.findByMail(username);
	}

	public User persistRegularUser(User user) {
		user.setUserRoles(new HashSet<String>(Arrays.asList(UserRole.BASIC)));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		return userDao.persist(user);
	}

	public User persistBranchUser(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUserRoles(new HashSet<String>(Arrays.asList(
				UserRole.BASIC, UserRole.BRANCH)));
		user.setEnabled(false);
		return userDao.persist(user);
	}

	public User persistAdminC1User(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUserRoles(new HashSet<String>(Arrays.asList(
				UserRole.BASIC, UserRole.ADMIN_C1)));
		user.setEnabled(false);
		return userDao.persist(user);
	}

	public User persistAdminC2User(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setUserRoles(new HashSet<String>(Arrays.asList(
				UserRole.BASIC, UserRole.ADMIN_C1, UserRole.ADMIN_C2)));
		user.setEnabled(false);
		return userDao.persist(user);
	}
}






/*


public void sendMail(String from, String subject, String message) {
	SimpleMailMessage mailMessage = new SimpleMailMessage();
	mailMessage.setFrom(from);
	mailMessage.setSubject(subject);
	mailMessage.setText(message);
	MailSender mailSender = new JavaMailSenderImpl();
	mailSender.send(mailMessage);
}*/