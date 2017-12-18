package com.registration.db;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

import com.registration.core.RepositoryException;
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class SubjectRepositoryImpl implements SubjectRepository {
	private Map<String, Subject> db = new ConcurrentHashMap<>();

	@Override
	public void add(Subject subject) {
		Objects.requireNonNull(subject, "Null subject ");
		
		if ( this.db.containsKey(subject.getCode().getValue()) ) {
			throw new RepositoryException("Duplicate key");
		}
		
		this.db.put(subject.getCode().getValue(), subject);
	}

	@Override
	public Optional<Subject> getByCode(NonEmptyString code) {
		Objects.requireNonNull(code, "Null code "); 
		
		return Optional.ofNullable(this.db.get(code.getValue()));
	}

	@Override
	public void update(Subject subject) {
		Objects.requireNonNull(subject, "Null subject ");
		
		this.db.put(subject.getCode().getValue(), subject);

	}

	@Override
	public void delete(Subject subject) {
		Objects.requireNonNull(subject, "Null subject ");
		
		this.db.remove(subject.getCode().getValue());
	}

	@Override
	public Collection<Subject> getAll() {
		return db.values();
	}
}
