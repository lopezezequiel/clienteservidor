package com.clienteservidor.preciosclaros.service;

import java.util.Set;

import org.springframework.security.access.annotation.Secured;

import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.model.UserRole;

public interface SessionService {

	User getUser();

	void signout();

	void removeFromCart(int id);

	Product addToCart(int id);

	void emptyCart();

	Set<Product> getCart();

	@Secured({UserRole.BASIC, UserRole.BRANCH, UserRole.ADMIN_C1, UserRole.ADMIN_C2})
	void signin();
}
