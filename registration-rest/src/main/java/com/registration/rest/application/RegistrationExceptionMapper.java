package com.registration.rest.application;

import java.util.NoSuchElementException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Provider
public class RegistrationExceptionMapper implements javax.ws.rs.ext.ExceptionMapper<Throwable>{
	
	private static final Logger logger = LoggerFactory.getLogger(RegistrationExceptionMapper.class);
	
	@Override
	public Response toResponse(Throwable exception) {
		if (logger.isErrorEnabled()) {
			logger.error(exception.getMessage(), exception);
		}
		
		if (exception instanceof NoSuchElementException) {
			return Response.status(Response.Status.NOT_FOUND).entity(Response.Status.NOT_FOUND).build();			
		}

		if (exception instanceof IllegalArgumentException) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Response.Status.BAD_REQUEST).build();			
		}

		if (exception instanceof NullPointerException) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Response.Status.BAD_REQUEST).build();			
		}
		
		if (exception instanceof IllegalStateException) {
			return Response.status(Response.Status.BAD_REQUEST).entity(Response.Status.BAD_REQUEST).build();			
		}		
		
		return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
	}
}