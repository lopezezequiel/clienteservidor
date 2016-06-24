package com.clienteservidor.preciosclaros.restapi;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class GenericController<T> {

	@Autowired
	protected T service;
}
