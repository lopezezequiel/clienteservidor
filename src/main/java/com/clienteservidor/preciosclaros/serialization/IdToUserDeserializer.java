package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.dao.UserDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.clienteservidor.preciosclaros.model.User;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Transactional
public class IdToUserDeserializer extends JsonDeserializer<User> {

	@Autowired
	private UserDao userDao;

	@Override
	public User deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        return userDao.findById(node.asInt());
	}
}
