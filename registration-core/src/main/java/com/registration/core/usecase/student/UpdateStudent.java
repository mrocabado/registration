package com.registration.core.usecase.student;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Student;
import com.registration.core.usecase.student.db.StudentRepository;


public class UpdateStudent implements UseCase<Boolean> {
	
	private Optional<Boolean> result = Optional.of(false);
	private StudentRepository studentRepo;
	private Student student;
	
	private UpdateStudent(Student student, StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
		this.student = student;
	}
	
	@Override
	public void execute() {
		
		Optional<Student> repoResult = this.studentRepo.getById(this.student.getId()); 

		repoResult.ifPresent(stu -> this.update(this.student));		
	}

	@Override
	public Optional<Boolean> getResult() {
		return result;
	}

	public static UseCase<Boolean> newInstance(Student student, StudentRepository studentRepo) {
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(student)) {
			error.append("Null student ");
		}
		if (Objects.isNull(studentRepo)) {
			error.append("Null studentRepo ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}
		
		return new UpdateStudent(student, studentRepo);
	}
	
	private void update(Student student){
		this.studentRepo.update(student);
		this.result = Optional.of(true);
	}
}
