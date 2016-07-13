package com.clienteservidor.preciosclaros.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="The entity does not exist")
public class EntityNotFoundException extends RuntimeException {

}
