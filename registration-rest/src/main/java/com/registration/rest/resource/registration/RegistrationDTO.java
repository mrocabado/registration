package com.registration.rest.resource.registration;

import java.util.Objects;

import com.registration.core.entity.Registration;

public class RegistrationDTO {

	private String studentId;
	private String subjectCode;
	
	RegistrationDTO() {
		//do nothing
	}
	
	RegistrationDTO(String studentId, String subjectCode) {
		this.studentId = studentId;
		this.subjectCode = subjectCode;
	}
	
	
	public static RegistrationDTO newInstance(Registration registration) {
		Objects.requireNonNull(registration, "Null registration " );
		
		return new RegistrationDTO(registration.getStudentId().getValue(), registration.getSubjectCode().getValue());
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getSubjectCode() {
		return subjectCode;
	}

	public void setSubjectCode(String subjectCode) {
		this.subjectCode = subjectCode;
	}	
	
}
