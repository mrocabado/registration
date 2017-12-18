package com.registration.db;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import com.registration.core.entity.Registration;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.registration.db.RegistrationRepository;

public class RegistrationRepositoryImpl implements RegistrationRepository {
	private Map<String, Registration> db = new ConcurrentHashMap<>();
	
	
	@Override
	public void save(List<Registration> registrations) {
		Objects.requireNonNull(registrations, "Null registrations ");
		
		for ( Registration reg : registrations) {
			this.db.put(reg.getKey(), reg);
		}

	}

	@Override
	public void delete(Registration registration) {
		Objects.requireNonNull(registration, "Null registration ");
		
		this.db.remove(registration.getKey());
		
	}

	@Override
	public List<Registration> getBySubjectCode(NonEmptyString code) {
		Objects.requireNonNull(code, "Null code ");
		
		return db.values()
					.stream()
					.filter(reg -> reg.getSubjectCode().equals(code))
					.collect(Collectors.toList());
	}

	@Override
	public List<Registration> getByStudentId(NonEmptyString id) {
		Objects.requireNonNull(id, "Null id ");
			
		return db.values()
				.stream()
				.filter(reg -> reg.getStudentId().equals(id))
				.collect(Collectors.toList());
	}

}
