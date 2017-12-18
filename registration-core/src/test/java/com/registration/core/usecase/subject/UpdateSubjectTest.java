package com.registration.core.usecase.subject;

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
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class UpdateSubjectTest {
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
			UpdateSubject.newInstance(nullSubject, subjectRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			SubjectRepository nullRepo = null;
			UpdateSubject.newInstance(subject, nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_subject_not_found_result_is_false() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);
		when( subjectRepo.getByCode(anyObject() ) ).thenReturn(Optional.empty());

		UseCase<Boolean> useCase = UpdateSubject.newInstance(subject, subjectRepo);
		useCase.execute();
		
		assertFalse(useCase.getResult().get());
	}
	
	
	@Test
	public void when_subject_found_result_is_true() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);
		when( subjectRepo.getByCode(anyObject() ) ).thenReturn(Optional.of(subject));

		UseCase<Boolean> useCase = UpdateSubject.newInstance(subject, subjectRepo);
		useCase.execute();
		
		assertTrue(useCase.getResult().get());
	}		
}

