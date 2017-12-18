package com.registration.core.usecase.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
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

public class UpdateStudentTest {
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
			Student nullStudent = null;
			UpdateStudent.newInstance(nullStudent, studentRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			StudentRepository nullRepo = null;
			UpdateStudent.newInstance(student, nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_student_not_found_result_is_false() {

		StudentRepository studentRepo = mock(StudentRepository.class);
		when( studentRepo.getById(anyObject() ) ).thenReturn(Optional.empty());

		UseCase<Boolean> useCase = UpdateStudent.newInstance(student, studentRepo);
		useCase.execute();
		
		assertFalse(useCase.getResult().get());
	}
	
	
	@Test
	public void when_student_found_result_is_true() {

		StudentRepository studentRepo = mock(StudentRepository.class);
		when( studentRepo.getById(anyObject() ) ).thenReturn(Optional.of(student));

		UseCase<Boolean> useCase = UpdateStudent.newInstance(student, studentRepo);
		useCase.execute();
		
		assertTrue(useCase.getResult().get());
	}		
}

