package com.registration.core.usecase.subject.db;

import java.util.Collection;
import java.util.Optional;

import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;

public interface SubjectRepository {

	void add(Subject subject);

	Optional<Subject> getByCode(NonEmptyString code);

	void update(Subject subject);

	void delete(Subject subject);
	
	Collection<Subject> getAll();
}
