package com.registration.core.usecase.subject;

import java.util.Objects;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

import com.registration.core.UseCase;
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.subject.db.SubjectRepository;

public class DeleteSubject implements UseCase<Subject> {
	
	private Optional<Subject> result = Optional.empty();
	private SubjectRepository subjectRepo;
	private NonEmptyString code;
	
	private DeleteSubject(NonEmptyString code, SubjectRepository subjectRepo) {
		this.subjectRepo = subjectRepo;
		this.code = code;
	}
	
	@Override
	public void execute() {
		
		Optional<Subject> repoResult = this.subjectRepo.getByCode(this.code); 

		repoResult.ifPresent(subject -> this.delete(subject));		
	}

	@Override
	public Optional<Subject> getResult() {
		return result;
	}

	public static UseCase<Subject> newInstance(NonEmptyString code, SubjectRepository subjectRepo) {
		StringBuilder error = new StringBuilder();
		
		if (Objects.isNull(code)) {
			error.append("Null subject code ");
		}
		if (Objects.isNull(subjectRepo)) {
			error.append("Null subjectRepo ");
		}	
		
		if (StringUtils.isNotEmpty(error.toString())) {
			throw new NullPointerException(error.toString().trim());
		}
		
		return new DeleteSubject(code, subjectRepo);
	}
	
	private void delete(Subject subject){
		this.subjectRepo.delete(subject);
		this.result = Optional.of(subject);
	}
}
