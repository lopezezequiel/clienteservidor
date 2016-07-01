package com.clienteservidor.preciosclaros.restapi;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.UserService;

@RestController
@RequestMapping("/api_v1")
public class UserController extends GenericController<UserService>  {

	@RequestMapping("user")
	public User getUser() {
		return service.getUser();
	}

	@RequestMapping(value="regularUsers", method=RequestMethod.POST)
	public User persist(@RequestBody User user) {
		return service.persistRegularUser(user);
	}
}
