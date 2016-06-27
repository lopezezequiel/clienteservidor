package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;
import java.util.Collection;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import com.clienteservidor.preciosclaros.model.GenericEntity;;

public class EntityCollectionToIdCollectionSerializer extends JsonSerializer<Collection<GenericEntity>>{

	@Override
	public void serialize(Collection<GenericEntity> entities, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeStartArray();
		for (GenericEntity entity : entities) {
			gen.writeNumber(entity.getId());
		}
		gen.writeEndArray();
	}

}
