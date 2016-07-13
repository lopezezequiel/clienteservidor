package com.clienteservidor.preciosclaros.restapi;


import java.util.Collection;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.model.User;
import com.clienteservidor.preciosclaros.service.UserService;

@RestController
@RequestMapping("/api_v1")
public class UserController extends GenericController<UserService>  {


	@RequestMapping(value="users", method=RequestMethod.POST)
	public User persist(@RequestBody User user) {
		System.out.println(user.getMail());
		System.out.println(user.getName());
		System.out.println(user.getPassword());
		System.out.println(user.getCart());
		System.out.println(user.getId());
		System.out.println(user.getUserRoles());
		System.out.println(user.getVersion());
		return service.persist(user);
	}
	
	@RequestMapping(value="users", method=RequestMethod.GET)
	public Collection<User> findAll(
			@RequestParam(value="offset", required=false) Integer offset,
			@RequestParam(value="limit", required=false) Integer limit,
			@RequestParam(value="q", required=false) String query) {
		return service.findAll(offset, limit, query);
	}

	@RequestMapping(value="users/length", method=RequestMethod.GET)
	public int size(
			@RequestParam(value="q", required=false) String query) {
		return service.size(query);
	}

	@RequestMapping(value="users/{id}", method=RequestMethod.GET)
	public User findById(
			@PathVariable("id") Integer id) {
		return service.findById(id);
	}
}
