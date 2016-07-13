package com.clienteservidor.preciosclaros.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.CONFLICT, reason="The entity already exists")
public class EntityAlreadyExistsException extends RuntimeException {

}
