package com.registration.core.usecase.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.BeforeClass;
import org.junit.Test;

import com.registration.core.UseCase;
import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.student.db.StudentRepository;

public class DeleteStudentTest {
	private static Student student;
	
	@BeforeClass
	public static void setUpSuite() {
		student = Student.newInstance(NonEmptyString.valueOf("id"), 
				NonEmptyString.valueOf("lastName"), 
				NonEmptyString.valueOf("firstName")
				);
	}

	@Test
	public void when_invalid_dependecies_provided_error_is_throw() {
		try {
			StudentRepository studentRepo = mock(StudentRepository.class);
			NonEmptyString nullId = null;
			DeleteStudent.newInstance(nullId, studentRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			StudentRepository nullRepo = null;
			DeleteStudent.newInstance(student.getId(), nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_student_not_found_result_is_empty() {

		StudentRepository studentRepo = mock(StudentRepository.class);
		when( studentRepo.getById(anyObject() ) ).thenReturn(Optional.empty());

		UseCase<Student> useCase = DeleteStudent.newInstance(student.getId(), studentRepo);
		useCase.execute();
		
		assertFalse(useCase.getResult().isPresent());
	}
	
	
	@Test
	public void when_student_found_result_is_present() {

		StudentRepository studentRepo = mock(StudentRepository.class);
		when( studentRepo.getById(anyObject() ) ).thenReturn(Optional.of(student));

		UseCase<Student> useCase = DeleteStudent.newInstance(student.getId(), studentRepo);
		useCase.execute();
		
		assertNotNull(useCase.getResult().get());
	}		
}

