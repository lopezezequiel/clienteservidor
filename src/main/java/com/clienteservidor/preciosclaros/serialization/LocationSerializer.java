package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import com.clienteservidor.preciosclaros.model.Location;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class LocationSerializer extends JsonSerializer<Location>{

	public void serialize(Location location, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {

		gen.writeNumberField("latitude", location.getLatitude());
		gen.writeNumberField("longitude", location.getLongitude());
	}

}
