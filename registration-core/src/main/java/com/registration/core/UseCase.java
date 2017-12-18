package com.registration.core;

import java.util.Optional;

/**
 * General interface to represent a Use Case. The use case is modeled as a Command pattern.
 * All required input are provided and validated at UseCase creation time.
 * After execution the UseCase may return a result that can be queried using getResult()
 * 
 * @author Marcelo Rocabado
 *
 * @param <E>
 */
public interface UseCase<E> {
	public void execute();
	public Optional<E> getResult();
	
	public static class UseCaseException extends RuntimeException {

		/**
		 * 
		 */
		private static final long serialVersionUID = 2758418509257012412L;
		
		public UseCaseException() {
			
		}
		
		public UseCaseException(String message) {
			super(message);
		}
		
	}
}