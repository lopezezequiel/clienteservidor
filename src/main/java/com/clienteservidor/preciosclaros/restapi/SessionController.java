package com.clienteservidor.preciosclaros.restapi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.SessionService;

@RestController
@RequestMapping("/api_v1/session")
public class SessionController {

	@Autowired
	private SessionService service;
	
	@RequestMapping(value = "signout", method= RequestMethod.POST)
	public void signout(){
		service.signout();
	}

	@RequestMapping(value = "signin", method= RequestMethod.POST)
	public void signin(){
	}

	@RequestMapping("user")
	public User getUser() {
		return service.getUser();
	}

	@RequestMapping("user/cart")
	public Set<Product> getAll() {
		return service.getCart();
	}

	@RequestMapping(value="user/cart", method=RequestMethod.DELETE)
	public void emptyCart() {
		service.emptyCart();
	}

	@RequestMapping(value="user/cart/{id}", method=RequestMethod.POST)
	public Product addToCart(@PathVariable("id") int id) {
		return service.addToCart(id);
	}

	@RequestMapping(value="user/cart/{id}", method=RequestMethod.DELETE)
	public void removeFromCart(@PathVariable("id") int id) {
		service.removeFromCart(id);
	}
}
