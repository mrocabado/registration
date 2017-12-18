package com.registration.rest.resource;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.ServletContext;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.beanutils.PropertyUtils;

import com.registration.rest.resource.student.StudentResourceDTO;
import com.registration.rest.resource.subject.SubjectResourceDTO;

public class AbstractResource {
	@Context
	protected UriInfo uriInfo;
	
	@Context
	protected HttpHeaders httpHeaders;
	
	@Context
	protected ServletContext servletContext;
	
	protected List<StudentResourceDTO> filterStudents(List<StudentResourceDTO> data) {
		String field = this.uriInfo.getQueryParameters().getFirst("field");
        String value = this.uriInfo.getQueryParameters().getFirst("value");
        
        List<StudentResourceDTO> result = data;
        
        if (Objects.isNull(field) || Objects.isNull(value)) {
        	return result;
        }
        
        try {
			StudentResourceDTO.class.getDeclaredField(field);
			
			result = data
						.stream()
						.filter( obj -> hasValueInField(obj, field, value)  )
						.collect( Collectors.toList() );

		} catch ( Exception e) {
			//do nothing, filtering won't work.
		}
        
		return result;
	}
	
	protected List<SubjectResourceDTO> filterSubjects(List<SubjectResourceDTO> data) {
		String field = this.uriInfo.getQueryParameters().getFirst("field");
        String value = this.uriInfo.getQueryParameters().getFirst("value");
        
        List<SubjectResourceDTO> result = data;
        
        if (Objects.isNull(field) || Objects.isNull(value)) {
        	return result;
        }
        
        try {
        	SubjectResourceDTO.class.getDeclaredField(field);
			
			result = data
						.stream()
						.filter( obj -> hasValueInField(obj, field, value)  )
						.collect( Collectors.toList() );

		} catch ( Exception e) {
			//do nothing, disable filtering
		}
        
		return result;
	}
	
	private boolean hasValueInField(Object obj, String field, String value)  {
		boolean result = true;
		try {
			String fieldValue = (String) PropertyUtils.getProperty(obj, field);
			result = value.equals( fieldValue );
		} catch (Exception e) {
			//do nothing, disable filtering 
		}
		
		return result;
	}
}
