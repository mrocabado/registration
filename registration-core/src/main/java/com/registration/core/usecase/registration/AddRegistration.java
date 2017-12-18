package com.registration.core.usecase.registration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Registration;
import com.registration.core.entity.Student;
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.registration.db.RegistrationRepository;
import com.registration.core.usecase.student.db.StudentRepository;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class AddRegistration implements UseCase<List<Registration>> {
	private Optional<List<Registration>> result = Optional.empty();
	private List<String> subjectCodes;
	private List<String> studentIds;
	private RegistrationRepository registrationRepo;
	private StudentRepository studentRepo;
	private SubjectRepository subjectRepo;
	
	private AddRegistration(List<String> subjectCodes, List<String> studentIds,
			RegistrationRepository registrationRepo, StudentRepository studentRepo, SubjectRepository subjectRepo) {
		this.subjectCodes = subjectCodes;
		this.studentIds = studentIds;
		this.registrationRepo = registrationRepo;
		this.studentRepo = studentRepo;
		this.subjectRepo = subjectRepo;
	}

	public static UseCase<List<Registration>> newInstance(NonEmptyString studentId, List<String> subjectCodes,
			RegistrationRepository registrationRepo, StudentRepository studentRepo, SubjectRepository subjectRepo) {
		
		validate(registrationRepo, studentRepo, subjectRepo);
		
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(studentId)) {
			error.append("Null studentId ");
		}
		if (Objects.isNull(subjectCodes)) {
			error.append("Null subjectCodes ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}	

		return new AddRegistration(subjectCodes, Arrays.asList(studentId.getValue()),
				registrationRepo, studentRepo, subjectRepo);
	}
	
	public static UseCase<List<Registration>> newInstance(List<String> studentIds, NonEmptyString subjectCode,
			RegistrationRepository registrationRepo, StudentRepository studentRepo, SubjectRepository subjectRepo) {
		
		validate(registrationRepo, studentRepo, subjectRepo);
		
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(studentIds)) {
			error.append("Null studentIds ");
		}
		if (Objects.isNull(subjectCode)) {
			error.append("Null subjectCode ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}		
		
		return new AddRegistration(Arrays.asList(subjectCode.getValue()), studentIds,
				registrationRepo, studentRepo, subjectRepo);

	}

	@Override
	public void execute() {
		List<Registration> registrations = new ArrayList<>();
		
		List<Student> students = studentIds
									.stream()
									.map( id -> this.studentRepo.getById( NonEmptyString.valueOf(id) ) )
									.filter( opt -> opt.isPresent())
									.map( opt -> opt.get())
									.collect(Collectors.toList());
		
		List<Subject> subjects = subjectCodes
									.stream()
									.map( code -> this.subjectRepo.getByCode( NonEmptyString.valueOf(code) ) )
									.filter( opt -> opt.isPresent())
									.map( opt -> opt.get())
									.collect(Collectors.toList());
		
		for (Student student : students) 
			for (Subject subject : subjects) {
				registrations.add( Registration.newInstance(student, subject));
			}
		
		this.registrationRepo.save(registrations); 
		
		this.result = Optional.of(registrations);		
	}

	@Override
	public Optional<List<Registration>> getResult() {
		return result;
	}
	
	private static void validate(RegistrationRepository registrationRepo, StudentRepository studentRepo, SubjectRepository subjectRepo) {
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(registrationRepo)) {
			error.append("Null registrationRepo ");
		}
		if (Objects.isNull(studentRepo)) {
			error.append("Null studentRepo ");
		}	
		if (Objects.isNull(subjectRepo)) {
			error.append("Null subjectRepo ");
		}			
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}	
	}

}
