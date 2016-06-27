package com.clienteservidor.preciosclaros.serviceImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.stereotype.Service;

import com.clienteservidor.preciosclaros.service.RoleService;

@Service("roleService")
public class RoleServiceImpl implements RoleService {

	public Collection<String> findAll() {
		Collection<String> roles = new ArrayList<String>();
		roles = Arrays.asList("ROLE_NORMAL", "ROLE_ROOT", "ROLE_ADMIN",
				"ROLE_BRANCH");
		
		return roles;
	}

}
