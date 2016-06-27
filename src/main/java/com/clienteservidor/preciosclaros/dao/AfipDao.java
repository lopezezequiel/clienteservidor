package com.clienteservidor.preciosclaros.dao;

import com.clienteservidor.preciosclaros.model.Afip;

public interface AfipDao {
	public Afip findByCuit(String cuit);
}
