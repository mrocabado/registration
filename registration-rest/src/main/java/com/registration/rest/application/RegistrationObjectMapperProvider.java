package com.registration.rest.application;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import javax.ws.rs.ext.ContextResolver;
import javax.ws.rs.ext.Provider;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
//import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

@Provider
public class RegistrationObjectMapperProvider implements ContextResolver<ObjectMapper> {
    private ObjectMapper jsonMapper;
    
    public RegistrationObjectMapperProvider() {
       createDefaultMapper();
    }


	@Override
	public ObjectMapper getContext(Class<?> type) {
		 return jsonMapper;
	}

	private void createDefaultMapper() {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
		jsonMapper = new ObjectMapper();
		jsonMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, true);
		jsonMapper.setSerializationInclusion(Include.NON_NULL);
//		jsonMapper.registerModule(new JsonOrgModule());
		jsonMapper.setDateFormat(df);	
	}
}
