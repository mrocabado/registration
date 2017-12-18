package com.registration.core.usecase.registration.db;

import java.util.List;

import com.registration.core.entity.Registration;
import com.registration.core.entity.type.NonEmptyString;

public interface RegistrationRepository {

	void save(List<Registration> registrations);
	
	void delete(Registration registration);

	List<Registration> getBySubjectCode(NonEmptyString code);

	List<Registration> getByStudentId(NonEmptyString id);
}
