package com.registration.rest.bootstrap;

import java.util.HashMap;
import java.util.Map;

import com.registration.core.usecase.registration.db.RegistrationRepository;
import com.registration.core.usecase.student.db.StudentRepository;
import com.registration.core.usecase.subject.db.SubjectRepository;
import com.registration.db.RegistrationRepositoryImpl;
import com.registration.db.StudentRepositoryImpl;
import com.registration.db.SubjectRepositoryImpl;

public class RepoFactory {

	private  Map<Class<?>, Object> repoByClass = new HashMap<>();
	
	public static final RepoFactory instance = new RepoFactory();
	
	private RepoFactory()  {
		try {
			//
			repoByClass.put(SubjectRepository.class, new SubjectRepositoryImpl());
			repoByClass.put(StudentRepository.class, new StudentRepositoryImpl());
			repoByClass.put(RegistrationRepository.class, new RegistrationRepositoryImpl());

		} catch (Exception e) {
			throw new IllegalStateException(e);
		}		
	}
	
	public Object make(Class<?> clazz){
		return repoByClass.get(clazz);
	}
}
