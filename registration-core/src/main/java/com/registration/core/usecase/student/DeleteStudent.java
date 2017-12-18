package com.registration.core.usecase.student;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.student.db.StudentRepository;


public class DeleteStudent implements UseCase<Student> {
	
	private Optional<Student> result = Optional.empty();
	private StudentRepository studentRepo;
	private NonEmptyString id;
	
	private DeleteStudent(NonEmptyString id, StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
		this.id = id;
	}
	
	@Override
	public void execute() {
		
		Optional<Student> repoResult = this.studentRepo.getById(this.id); 

		repoResult.ifPresent(student -> this.delete(student));		
	}

	@Override
	public Optional<Student> getResult() {
		return result;
	}

	public static UseCase<Student> newInstance(NonEmptyString id, StudentRepository studentRepo) {
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(id)) {
			error.append("Null id ");
		}
		if (Objects.isNull(studentRepo)) {
			error.append("Null studentRepo ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}
		
		return new DeleteStudent(id, studentRepo);
	}
	
	private void delete(Student student){
		this.studentRepo.delete(student);
		this.result = Optional.of(student);
	}
}
