package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;

import com.clienteservidor.preciosclaros.dao.UnitDao;
import com.clienteservidor.preciosclaros.model.GenericEntity;
import com.clienteservidor.preciosclaros.model.Location;
import com.clienteservidor.preciosclaros.model.Unit;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocationSerializer extends JsonSerializer<Location>{

	public void serialize(Location location, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		/*
		if(entity != null) {
			gen.writeNumber(entity.getId());
		} else {
			gen.writeNull();
		}
		*/

		gen.writeNumberField("latitude", location.getLatitude());
		gen.writeNumberField("longitude", location.getLongitude());
	}

}
