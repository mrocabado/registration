package com.registration.db;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.registration.core.RepositoryException;
import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.student.db.StudentRepository;

public class StudentRepositoryImpl implements StudentRepository {
	private Map<String, Student> db = new ConcurrentHashMap<>();
	
	@Override
	public void add(Student student) {
		Objects.requireNonNull(student, "Null student ");
		
		if ( this.db.containsKey(student.getId().getValue() ) ) {
			throw new RepositoryException("Duplicate key");
		}
		
		this.db.put(student.getId().getValue(), student);
	}

	@Override
	public Optional<Student> getById(NonEmptyString id) {
		Objects.requireNonNull(id,  "Null id "); 
		
		return Optional.ofNullable(this.db.get(id.getValue()));
	}

	@Override
	public void update(Student student) {
		Objects.requireNonNull(student, "Null student ");
		
		this.db.put(student.getId().getValue(), student);
	}

	@Override
	public void delete(Student student) {
		Objects.requireNonNull(student, "Null student ");
		
		this.db.remove(student.getId().getValue());

	}

	@Override
	public Collection<Student> getAll() {
		return db.values();
	}

}
