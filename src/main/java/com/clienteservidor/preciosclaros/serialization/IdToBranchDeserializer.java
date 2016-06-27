package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.dao.BranchDao;
import com.clienteservidor.preciosclaros.model.Branch;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@Transactional
public class IdToBranchDeserializer extends JsonDeserializer<Branch> {

	@Autowired
	private BranchDao branchDao;

	@Override
	public Branch deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = p.getCodec().readTree(p);

        return branchDao.findById(node.asInt());
	}
}
