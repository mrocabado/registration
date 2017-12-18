package com.registration.rest.resource.student;

import java.util.Objects;

import javax.validation.constraints.NotNull;

import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;

public class StudentResourceDTO {
	
	private String id;
	
	@NotNull
	private String lastName;
	
	@NotNull
	private String firstName;
	
	StudentResourceDTO( ) {
		//do nothing
	}

	StudentResourceDTO(String id, String lastName, String firstName) {
		this.id = id;
		this.lastName = lastName;
		this.firstName = firstName;
	}
	
	public Student toStudent() {
		return Student.newInstance(NonEmptyString.valueOf(this.getId()), 
								NonEmptyString.valueOf(this.getLastName()), 
								NonEmptyString.valueOf(this.getFirstName()));
	}
	
	public static StudentResourceDTO newInstance(Student student) {
		Objects.requireNonNull(student, "Null student " );
		
		return new StudentResourceDTO(student.getId().getValue(), student.getLastName().getValue(), student.getFirstName().getValue());
	}	

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	
}
