package com.registration.rest.resource.subject;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;

public class SubjectResourceDTO {
	
	private String code;
	
	@NotNull
	private String title;
	
	@NotNull
	private String description;

	SubjectResourceDTO(){
		//do nothing
	}
	
	SubjectResourceDTO(String code, String title, String description) {
		this.code = code;
		this.title = title;
		this.description = description;
	}

	public Subject toSubject() {
		return Subject.newInstance(NonEmptyString.valueOf(this.getCode()), 
								NonEmptyString.valueOf(this.getTitle()), 
								NonEmptyString.valueOf(this.getDescription()));
	}

	public static SubjectResourceDTO newInstance(Subject subject) {
		Objects.requireNonNull(subject, "Null subject " );
		
		return new SubjectResourceDTO(subject.getCode().getValue(), subject.getTitle().getValue(), subject.getDescription().getValue());
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}	
}
