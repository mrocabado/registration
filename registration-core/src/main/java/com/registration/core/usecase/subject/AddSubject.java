package com.registration.core.usecase.subject;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Subject;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class AddSubject implements UseCase<Subject> {
	private Optional<Subject> result = Optional.empty();
	private SubjectRepository subjectRepo;
	private Subject subject;
	
	private AddSubject(Subject subject, SubjectRepository subjectRepo) {
		this.subjectRepo = subjectRepo;
		this.subject = subject;
	}

	@Override
	public void execute() {
		this.subjectRepo.add(this.subject);	
		
		this.result = Optional.of(this.subject);
	}

	@Override
	public Optional<Subject> getResult() {
		return result;
	}

	public static UseCase<Subject> newInstance(Subject subject, SubjectRepository subjectRepo) {
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(subject)) {
			error.append("Null subject ");
		}
		if (Objects.isNull(subjectRepo)) {
			error.append("Null subjectRepo ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}
		
		return new AddSubject(subject, subjectRepo);
	}

}
