package com.registration.core.usecase.subject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;

import org.junit.BeforeClass;
import org.junit.Test;

import com.registration.core.RepositoryException;
import com.registration.core.UseCase;
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class AddSubjectTest {
	private static Subject subject;
	
	@BeforeClass
	public static void setUpSuite() {
		subject = Subject.newInstance(NonEmptyString.valueOf("code"), 
								NonEmptyString.valueOf("title"), 
								NonEmptyString.valueOf("description")
								);
	}

	@Test
	public void when_invalid_dependecies_provided_error_is_throw() {
		try {
			SubjectRepository subjectRepo = mock(SubjectRepository.class);
			Subject nullSubject = null;
			AddSubject.newInstance(nullSubject, subjectRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			SubjectRepository nullRepo = null;
			AddSubject.newInstance(subject, nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_execution_throws_exception_result_is_empty() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);
		doThrow(RepositoryException.class).when(subjectRepo).add(subject);

		UseCase<Subject> useCase = AddSubject.newInstance(subject, subjectRepo);
		
		try {
			useCase.execute();
		} catch (RepositoryException e) {
			assertNotNull(e);
			assertFalse(useCase.getResult().isPresent());
		}	
	}
	
	@Test
	public void when_execution_does_not_throws_exception_result_is_not_empty() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);

		UseCase<Subject> useCase = AddSubject.newInstance(subject, subjectRepo);
		useCase.execute();
		
		assertTrue(useCase.getResult().isPresent());
	}		
}

