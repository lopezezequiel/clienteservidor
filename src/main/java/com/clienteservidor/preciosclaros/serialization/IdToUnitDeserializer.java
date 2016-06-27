package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.dao.UnitDao;
import com.clienteservidor.preciosclaros.model.Unit;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Transactional
public class IdToUnitDeserializer extends JsonDeserializer<Unit> {

	@Autowired
	private UnitDao unitDao;

	@Override
	public Unit deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        return unitDao.findById(node.asInt());
	}
}
