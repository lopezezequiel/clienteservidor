package com.clienteservidor.preciosclaros.serialization;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CommaSeparatedListToIntegerListDeserializer {
	public final static List<Integer> deserialize(String csl) {
		if(!Pattern.matches("^\\d+(,\\d+)*$", csl)) {
			//TODO
			throw new RuntimeException();
		}
			
		return Arrays.asList(csl.split(",")).stream().map(Integer::valueOf)
				.collect(Collectors.toList());
	}
}
