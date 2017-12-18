package com.registration.core.usecase.subject;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
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

public class DeleteSubjectTest {
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
			NonEmptyString nullCode = null;
			DeleteSubject.newInstance(nullCode, subjectRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
		
		try {
			SubjectRepository nullRepo = null;
			DeleteSubject.newInstance(subject.getCode(), nullRepo);
		} catch (NullPointerException e) {
			assertNotNull(e);
		}
	}
	
	@Test
	public void when_subject_not_found_result_is_empty() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);
		when( subjectRepo.getByCode(anyObject() ) ).thenReturn(Optional.empty());

		UseCase<Subject> useCase = DeleteSubject.newInstance(subject.getCode(), subjectRepo);
		useCase.execute();
		
		assertFalse(useCase.getResult().isPresent());
	}
	
	
	@Test
	public void when_subject_found_result_is_present() {

		SubjectRepository subjectRepo = mock(SubjectRepository.class);
		when( subjectRepo.getByCode(anyObject() ) ).thenReturn(Optional.of(subject));

		UseCase<Subject> useCase = DeleteSubject.newInstance(subject.getCode(), subjectRepo);
		useCase.execute();
		
		assertNotNull(useCase.getResult().get());
	}		
}

