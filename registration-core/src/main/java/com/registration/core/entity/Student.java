package com.registration.core.entity;

import java.util.Objects;

import com.registration.core.entity.type.NonEmptyString;

public class Student {
	
	private NonEmptyString id;
	private NonEmptyString lastName; 
	private NonEmptyString firstName;
	
	private Student(NonEmptyString id, NonEmptyString lastName, NonEmptyString fisrtName) {
		super();
		this.id = id;
		this.lastName = lastName;
		this.firstName = fisrtName;
	}	

	public static Student newInstance(NonEmptyString id, NonEmptyString lastName, NonEmptyString firstName) {
		Objects.requireNonNull(id, "Null id");
		Objects.requireNonNull(lastName, "Null lastName");
		Objects.requireNonNull(firstName, "Null firstName");
		
		return new Student(id, lastName, firstName);
	}
	
	public Student  withNewData(NonEmptyString lastName, NonEmptyString firstName) {
		return newInstance(this.getId(), lastName, firstName);
	}

	public NonEmptyString getId() {
		return id;
	}

	public NonEmptyString getLastName() {
		return lastName;
	}

	public NonEmptyString getFirstName() {
		return firstName;
	}	
}
