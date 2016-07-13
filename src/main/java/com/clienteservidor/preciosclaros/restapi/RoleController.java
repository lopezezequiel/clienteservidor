package com.clienteservidor.preciosclaros.restapi;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.clienteservidor.preciosclaros.service.RoleService;

@RestController
@RequestMapping("/api_v1/roles")
public class RoleController {
	
	@Autowired
	private RoleService roleService;

	@RequestMapping("")
	public Collection<String> findAll(){
		return roleService.findAll();
	}
}
