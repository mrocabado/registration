package com.registration.core.usecase.student.db;

import java.util.Collection;
import java.util.Optional;

import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;

public interface StudentRepository {

	void add(Student student);

	Optional<Student> getById(NonEmptyString id);

	void update(Student student);

	void delete(Student student);
	
	Collection<Student> getAll();	
}
