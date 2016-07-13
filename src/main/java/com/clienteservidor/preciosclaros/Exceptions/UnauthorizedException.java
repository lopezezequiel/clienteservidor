package com.clienteservidor.preciosclaros.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.FORBIDDEN, reason="You do not have permission to perform this action")
public class UnauthorizedException extends RuntimeException {

}
