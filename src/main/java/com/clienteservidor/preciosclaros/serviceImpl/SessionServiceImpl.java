package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.Iterator;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.clienteservidor.preciosclaros.Exceptions.EntityNotFoundException;
import com.clienteservidor.preciosclaros.dao.ProductDao;
import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.Session;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.SessionService;


@Transactional
@Service("sessionService")
public class SessionServiceImpl implements SessionService {

	@Autowired
	private HttpSession httpSession;

	@Autowired
	private UserDao userDao;

	@Autowired
	private ProductDao productDao;

	@Autowired
	private Session session;
	
	@Override
	public User getUser() {
				
		if(session.getUser() == null) {

			try {

				String mail = SecurityContextHolder.getContext().getAuthentication()
						.getName();
				session.setUser(userDao.findByMail(mail));

			} catch(EntityNotFoundException exception) {
				session.setUser(new User());
			}
		}
		
		return session.getUser();
	}

	@Override
	public void emptyCart() {
		User user = getUser();
		user.getCart().clear();

		if(user.getId() != null) {
			userDao.update(user);			
		}
	}

 	@Override
	public Product addToCart(int id) {
		User user = getUser();
		Product product = productDao.findById(id);
		user.getCart().add(product);

		if(user.getId() != null) {
			userDao.update(user);			
		}
		
		return product;
	}

	@Override
	public void removeFromCart(int id) {
		User user = getUser();

		Iterator<Product> iterator = user.getCart().iterator();
		while(iterator.hasNext()) {
			Product p = iterator.next();
			if(p.getId() == id) {
				iterator.remove();
			}
		}

		if(user.getId() != null) {
			userDao.update(user);			
		}
	}

	@Override
	public void signout() {
		httpSession.invalidate();		
	}

	@Override
	public void signin() {
	}

	@Override
	public Set<Product> getCart() {
		return getUser().getCart();
	}
}
