package com.clienteservidor.preciosclaros.restapi;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.UserService;

@RestController
@RequestMapping("/api_v1/auth")
public class AuthController extends GenericController<UserService> {

	@RequestMapping("signin")
	public User signin(){
		return service.getUser();
	}
}
