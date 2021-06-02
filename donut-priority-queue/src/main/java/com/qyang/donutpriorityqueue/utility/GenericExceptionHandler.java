package com.qyang.donutpriorityqueue.utility;

import java.util.Date;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.qyang.donutpriorityqueue.model.ErrorDetails;

/*
 * Definieren Sie eine einheitliche Ausnahmebehandlungsklasse mithilfe von 
 * @ControllerAdvice, anstatt in jedem Controller eine nach der anderen zu definieren.
 * @ExceptionHandler wird verwendet, um den Ausnahmetyp zu definieren, 
 * auf den die Funktion abzielt, und schließlich das Ausnahmeobjekt und
 * die Anforderungs-URL der Datei error.html zuzuordnen
 */


@RestController
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice(basePackages = {"com.atrify.donutpriorityqueue"})
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		ErrorDetails errorDetails = new ErrorDetails(new Date(), "Validation Failed",
				ex.getBindingResult().getFieldError().getField() + ", "
						+ ex.getBindingResult().getFieldError().getDefaultMessage());

		return new ResponseEntity<Object>(errorDetails, HttpStatus.BAD_REQUEST);
	}

	

}