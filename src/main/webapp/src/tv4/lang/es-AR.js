(function (global) {
	var lang = {
			INVALID_TYPE: "Tipo inválido: {type} (se esperaba {expected})",
			ENUM_MISMATCH: "No hay resultados para: {value}",
			ANY_OF_MISSING: "Los datos no coinciden con ninguno de los siguientes esquemas \"anyOf\"",
			ONE_OF_MISSING: "Los datos no coinciden con ninguno de los siguientes esquemas \"oneOf\"",
			ONE_OF_MULTIPLE: "Los datos son válidos para más de un esquema de \"oneOf\": indices {index1} and {index2}",
			NOT_PASSED: "Los datos validan con \"not\"",
			// Numeric errors
			NUMBER_MULTIPLE_OF: "El valor {value} no es multiplo de {multipleOf}",
			NUMBER_MINIMUM: "El valor {value} es menor que el mínimo {minimum}",
			NUMBER_MINIMUM_EXCLUSIVE: "El valor {value} es igual al mínimo exclusivo {minimum}",
			NUMBER_MAXIMUM: "El valor {value} es mayor que el máximo {maximum}",
			NUMBER_MAXIMUM_EXCLUSIVE: "El valor {value} es igual al máximo exclusivo {maximum}",
			NUMBER_NOT_A_NUMBER: "El valor {value} no es un número",
			// String errors
			STRING_LENGTH_SHORT: "El texto es demasiado corto ({length} caracteres), mínimo permitido {minimum}",
			STRING_LENGTH_LONG: "El texto es demasiado largo ({length} caracteres), máximo permitido {maximum}",
			STRING_PATTERN: "El texto no coincide con el patrón: {pattern}",
			// Object errors
			OBJECT_PROPERTIES_MINIMUM: "Too few properties defined ({propertyCount}), minimum {minimum}",
			OBJECT_PROPERTIES_MAXIMUM: "Too many properties defined ({propertyCount}), maximum {maximum}",
			OBJECT_REQUIRED: "Missing required property: {key}",
			OBJECT_ADDITIONAL_PROPERTIES: "Additional properties not allowed",
			OBJECT_DEPENDENCY_KEY: "Dependency failed - key must exist: {missing} (due to key: {key})",
			// Array errors
			ARRAY_LENGTH_SHORT: "Array is too short ({length}), minimum {minimum}",
			ARRAY_LENGTH_LONG: "Array is too long ({length}), maximum {maximum}",
			ARRAY_UNIQUE: "Array items are not unique (indices {match1} and {match2})",
			ARRAY_ADDITIONAL_ITEMS: "Additional items not allowed",
			// Format errors
			FORMAT_CUSTOM: "Falló la validación del formato ({message})",
			KEYWORD_CUSTOM: "Keyword failed: {key} ({message})",
			// Schema structure
			CIRCULAR_REFERENCE: "Circular $refs: {urls}",
			// Non-standard validation options
			UNKNOWN_PROPERTY: "Propiedad desconocida (no existe en el esquema)"
	};
	
	if (typeof define === 'function' && define.amd) {
		// AMD. Register as an anonymous module.
		define(['../tv4'], function(tv4) {
			tv4.addLanguage('es-AR', lang);
			return tv4;
		});
	} else if (typeof module !== 'undefined' && module.exports) {
		// CommonJS. Define export.
		var tv4 = require('../tv4');
		tv4.addLanguage('es-AR', lang);
		module.exports = tv4;
	} else {
		// Browser globals
		global.tv4.addLanguage('es-AR', lang);
	}
})(this);
