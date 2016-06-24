package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.clienteservidor.preciosclaros.model.GenericEntity;;

public class EntityToIdSerializer extends JsonSerializer<GenericEntity>{

	public void serialize(GenericEntity entity, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		/*
		if(entity != null) {
			gen.writeNumber(entity.getId());
		} else {
			gen.writeNull();
		}
		*/

		gen.writeNumber(entity.getId());
	}

}
