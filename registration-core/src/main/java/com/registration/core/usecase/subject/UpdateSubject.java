package com.registration.core.usecase.subject;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Subject;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class UpdateSubject implements UseCase<Boolean> {
	
	private Optional<Boolean> result = Optional.of(false);
	private SubjectRepository subjectRepo;
	private Subject subject;
	
	private UpdateSubject(Subject subject, SubjectRepository subjectRepo) {
		this.subjectRepo = subjectRepo;
		this.subject = subject;
	}
	
	@Override
	public void execute() {
		
		Optional<Subject> repoResult = this.subjectRepo.getByCode(this.subject.getCode()); 

		repoResult.ifPresent(sub -> this.update(this.subject));		
	}

	@Override
	public Optional<Boolean> getResult() {
		return result;
	}

	public static UseCase<Boolean> newInstance(Subject subject, SubjectRepository subjectRepo) {
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
		
		return new UpdateSubject(subject, subjectRepo);
	}
	
	private void update(Subject subject){
		this.subjectRepo.update(subject);		
		this.result = Optional.of(true);
	}
}
