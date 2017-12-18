package com.registration.core.entity;

import java.util.Objects;

import com.registration.core.entity.type.NonEmptyString;

public class Registration {

	private NonEmptyString studentId;
	private NonEmptyString subjectCode;
	
	private Registration(NonEmptyString studentId, NonEmptyString subjectCode) {
		this.studentId = studentId;
		this.subjectCode = subjectCode;
	}
	
	public static Registration newInstance(Student student, Subject subject) {
		Objects.requireNonNull(student, "Null student");
		Objects.requireNonNull(subject, "Null subject");
		
		return new Registration(student.getId(), subject.getCode());
	}

	public NonEmptyString getStudentId() {
		return studentId;
	}

	public NonEmptyString getSubjectCode() {
		return subjectCode;
	}
	
	public String getKey() {
		return String.format("%s - %s", getStudentId().getValue(), getSubjectCode().getValue());
	}
}
