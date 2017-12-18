package com.registration.core.usecase.student;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.registration.core.RepositoryException;
import com.registration.core.UseCase;
import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.student.db.StudentRepository;

public class AddStudentTest {
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
			AddStudent.newInstance(nullStudent, studentRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			StudentRepository nullRepo = null;
			AddStudent.newInstance(student, nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_execution_throws_exception_result_is_empty() {

		StudentRepository studentRepo = mock(StudentRepository.class);
		doThrow(RepositoryException.class).when(studentRepo).add(student);

		UseCase<Student> useCase = AddStudent.newInstance(student, studentRepo);
		
		try {
			useCase.execute();
		} catch (RepositoryException e) {
			assertNotNull(e);
			assertFalse(useCase.getResult().isPresent());
		}	
	}
	
	@Test
	public void when_execution_does_not_throws_exception_result_is_not_empty() {

		StudentRepository studentRepo = mock(StudentRepository.class);

		UseCase<Student> useCase = AddStudent.newInstance(student, studentRepo);
		useCase.execute();
		
		assertTrue(useCase.getResult().isPresent());
	}		
}

