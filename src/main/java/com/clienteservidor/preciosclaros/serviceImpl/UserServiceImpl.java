package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.Exceptions.EntityAlreadyExistsException;
import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.Exceptions.UnauthorizedException;
import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;
import com.clienteservidor.preciosclaros.service.MailService;
import com.clienteservidor.preciosclaros.service.SessionService;
import com.clienteservidor.preciosclaros.service.UserService;
import com.google.common.primitives.Bytes;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
	private static final int LIMIT = 1000;

    @Autowired
    private PasswordEncoder passwordEncoder;

	@Autowired
	private UserDao userDao;
	
	@Autowired
	private MailService mailService;
	
	@Autowired
	private SessionService sessionService;

	public int size(String query) {
		return userDao.size(query);
	}

	public Collection<User> findAll(Integer offset, Integer limit, String query) {
		offset = ((offset == null) || (offset < 0)) ? 0 : offset;
	    limit = ((limit == null) || (limit > LIMIT)) ? LIMIT : limit;
		return userDao.findAll(offset, limit, query);
	}

	/*
	@Override
	public User persist(User user) {
		
		try {
			userDao.findByMail(user.getMail());
			throw new EntityAlreadyExistsException();
		} catch(EntityNotFoundException exception) {
			
		}

		if(user.getUserRoles() == null) {
			user.setUserRoles(new HashSet<String>(Arrays.asList(UserRole.BASIC)));	
		} else {
			checkAuthorization(user.getUserRoles());
		}

		user.setUserRoles(new HashSet<String>(Arrays.asList(UserRole.BASIC)));
		user.setVersion(0);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setEnabled(true);
		
		user = userDao.persist(user);

	//	mailService.send(user.getMail(), "Precios Claros<lopezezequiel.09@gmail.com>", "Confirmación de registro en Precios Claros", "Gracias por registrarte en Precios Claros");
		
		return user;
	}

	private void checkAuthorization(Set<String> userRoles) {
		Set<String> authorization = getUserAuthorization();
		for(String role: userRoles) {
			if(!authorization.contains(role)) {
				throw new RuntimeException();
			}
		}		
	}

	private Set<String> getUserAuthorization() {
		Set<String> authorization = new HashSet<String>();

		try {
			Set<String> userRoles = sessionService.getUser().getUserRoles();
			for(String role: userRoles) {
				authorization.addAll(getAuthorizationByRole(role));
			}
			
		} catch (Exception e) {
			//anonymous user can create Basic User
			authorization.add(UserRole.BASIC);
		}
		
		return authorization;
	}

	private Set<String> getAuthorizationByRole(String role) {
		Map<String, Set<String>> authorizationTable = new HashMap<String, Set<String>>();

		authorizationTable.put(
				UserRole.ADMIN_C1,
				new HashSet<String>(Arrays.asList(
					UserRole.BASIC,
					UserRole.BRANCH
				))
		);

		authorizationTable.put(
				UserRole.ADMIN_C2,
				new HashSet<String>(Arrays.asList(
					UserRole.BASIC,
					UserRole.ADMIN_C1,
					UserRole.ADMIN_C2
				))
		);

		if(!authorizationTable.containsKey(role)) {
			throw new RuntimeException();
		}
		
		return authorizationTable.get(role);
	}
*/

	@Override
	public void enable(String key) {
		User user = userDao.findByHashCode(key);
		if(user.isEnabled()) {
			throw new RuntimeException();
		}
		user.setEnabled(true);
		user.setHashCode(null);

		mailService.send(user.getMail(), "Precios Claros<preciosclaros.servehttp.com@gmail.com>", "Bienvenido a Precios Claros", "Gracias por adherirse a Precios Claros");
	}

	@Override
	public User findById(int id) {
		return userDao.findById(id);
	}

	@Override
	public void update(User user) {
		user.setPassword(null);
		userDao.update(user);
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public User persist(User newUser) {
		
		//check if email is available
		try {
			userDao.findByMail(newUser.getMail());
			throw new EntityAlreadyExistsException();
		} catch(EntityNotFoundException exception) {
		}

		Set<String> roles = newUser.getUserRoles();

		System.out.print("12345678900987654313" + roles.toString());
		User sessionUser = sessionService.getUser();
		String password = newUser.getPassword();
		String message = "Gracias por registrarte en Precios Claros.";

		byte[] bytes = new byte[100];
		new Random().nextBytes(bytes);
		newUser.setHashCode(
				Base64.encodeBase64String(newUser.getMail().getBytes()) +
				"-" +
				Base64.encodeBase64String(bytes)
		);
		
		message = "Para activar tu cuenta dirigirse a la siguiente dirección: preciosclaros.servehttp.com:8080/userapp/index.html#verify-" + newUser.getHashCode();
		
		//default role is basic
		if(roles == null || roles.isEmpty()) {
			newUser.setUserRoles(new HashSet<String>(Arrays.asList(UserRole.BASIC)));	
		}

		//check permissions
		if(roles.contains(UserRole.ADMIN_C1) || roles.contains(UserRole.ADMIN_C2) || roles.contains(UserRole.BRANCH)) {
			message += "Tu clave es: " + newUser.getPassword();
			message += "Luego de activar tu cuenta debes cambiar tu clave por razones de seguridad";
			if(!sessionUser.getUserRoles().contains(UserRole.ADMIN_C2)) {
				throw new UnauthorizedException();
			}
		}

		newUser.setVersion(0);
		newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
		newUser.setEnabled(false);
		
		newUser = userDao.persist(newUser);

		mailService.send(newUser.getMail(), "Precios Claros<preciosclaros.servehttp.com@gmail.com>", "Confirmación de registro en Precios Claros", message);
		
		return newUser;
	}

}