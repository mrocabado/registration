package com.registration.core;

/**
 * Runtime exception used to wrap any persistence/db excption coming from the data access layer
 * in order to keep core layer from importing data access layer classes.
 * 
 * @author Marcelo Rocabado
 *
 * @param <E>
 */
public class RepositoryException extends RuntimeException {
	private static final long serialVersionUID = -7553114420532095418L;

	public RepositoryException(){
		
	}
	
	public RepositoryException(String message){
		super(message);
	}
	
	public RepositoryException(Throwable cause){
		super(cause);
	}
	
    public RepositoryException(String message, Throwable cause) {
        super(message, cause);
    }
	
}