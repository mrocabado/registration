package com.registration.core.usecase.student;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Student;
import com.registration.core.usecase.student.db.StudentRepository;

public class AddStudent implements UseCase<Student> {
	private Optional<Student> result = Optional.empty();
	private StudentRepository studentRepo;
	private Student student;
	
	private AddStudent(Student Student, StudentRepository studentRepo) {
		this.studentRepo = studentRepo;
		this.student = Student;
	}

	@Override
	public void execute() {
		this.studentRepo.add(this.student);	
		
		this.result = Optional.of(this.student);
	}

	@Override
	public Optional<Student> getResult() {
		return result;
	}

	public static UseCase<Student> newInstance(Student student, StudentRepository studentRepo) {
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
		
		return new AddStudent(student, studentRepo);
	}

}
