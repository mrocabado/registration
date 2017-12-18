package com.registration.rest.application;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.registration.rest.resource.student.StudentResource;
import com.registration.rest.resource.subject.SubjectResource;

@ApplicationPath("rest")
public class RegistrationApplication extends Application {
	public static final String VERSION = "1.0";
	private static final Logger logger =  LoggerFactory.getLogger(RegistrationApplication.class);
	
	private Set<Class<?>> perRequestComponents = new HashSet<>(Arrays.asList(
													SubjectResource.class,
													StudentResource.class,
													io.swagger.jaxrs.listing.ApiListingResource.class,
													io.swagger.jaxrs.listing.SwaggerSerializers.class,
													RegistrationObjectMapperProvider.class
													));
	private Set<Object> singletonComponents = new HashSet<>(Arrays.asList(
			  new RegistrationExceptionMapper()
			));
	
	public RegistrationApplication() {
		logger.info("RegistrationApplication()");
	}
	
	@Override
	public Set<Class<?>> getClasses() {
		return perRequestComponents;
	}
	
	@Override
	public Set<Object> getSingletons() {
		return singletonComponents;
	}
}