package com.clienteservidor.preciosclaros.service;

import com.clienteservidor.preciosclaros.model.Afip;

public interface AfipService {
	public Afip findByCuit(String cuit);
}
