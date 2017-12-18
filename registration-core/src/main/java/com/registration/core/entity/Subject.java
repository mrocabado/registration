package com.registration.core.entity;

import java.util.Objects;

import com.registration.core.entity.type.NonEmptyString;

public class Subject {

	private NonEmptyString code;
	private NonEmptyString title; 
	private NonEmptyString description;	
	
	
	private Subject(NonEmptyString code, NonEmptyString title, NonEmptyString description) {
		this.code = code;
		this.title = title;
		this.description = description;
	}
	
	
	public static Subject newInstance(NonEmptyString code, NonEmptyString title, NonEmptyString description) {
		Objects.requireNonNull(code, "Null code");
		Objects.requireNonNull(title, "Null title");
		Objects.requireNonNull(description, "Null description");
		
		return new Subject(code, title, description);
	}
	
	public Subject  withNewData(NonEmptyString title, NonEmptyString description) {
		return newInstance(this.getCode(), title, description);
	}
	

	public NonEmptyString getCode() {
		return code;
	}

	public NonEmptyString getTitle() {
		return title;
	}

	public NonEmptyString getDescription() {
		return description;
	}
	
}
