package com.clienteservidor.preciosclaros.restapi;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.Product;
import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.MailService;
import com.clienteservidor.preciosclaros.service.SessionService;
import com.clienteservidor.preciosclaros.service.UserService;

@RestController
@RequestMapping("/api_v1")
public class FooController {
	
	@Autowired
	private UserService userService;

	@RequestMapping("enable")
	public void enable(
			@RequestParam(value="key", required=true) String key){
		userService.enable(key);
	}
	
	/*

	@RequestMapping(value="reset-password", method=RequestMethod.POST)
	public void signout(
			@RequestParam(value="key", required=true) String key,
			@RequestParam(value="key", required=true) String key){
		userService.resetPassword(key, password);
	}*/
}
