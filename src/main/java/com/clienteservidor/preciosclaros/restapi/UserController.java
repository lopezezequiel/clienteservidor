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

	@RequestMapping(value="regularUsers", method=RequestMethod.POST)
	public User persistRegularUser(@RequestBody User user) {
		return service.persistRegularUser(user);
	}

	@RequestMapping(value="adminUsers", method=RequestMethod.POST)
	public User persistAdminUser(@RequestBody User user) {
		return service.persistAdminUser(user);
	}

	@RequestMapping(value="rootUsers", method=RequestMethod.POST)
	public User persistRootUser(@RequestBody User user) {
		return service.persistRootUser(user);
	}

	@RequestMapping(value="branchUsers", method=RequestMethod.POST)
	public User persistBranchUser(@RequestBody User user) {
		return service.persistBranchUser(user);
	}

	@RequestMapping("send")
	public void send() {
		service.sendMail("lopezezequiel.09@gmail.com", "lopez ezequiel", "Hola Mundo");
	}
}
