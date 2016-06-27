package com.clienteservidor.preciosclaros.serialization;

import java.io.IOException;

import com.clienteservidor.preciosclaros.model.Product;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class ProductToEanSerializer extends JsonSerializer<Product> {

	@Override
	public void serialize(Product product, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		gen.writeNumber(product.getEan13());
	}

}
