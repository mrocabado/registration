package com.registration.rest.resource.subject;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.registration.core.UseCase;
import com.registration.core.entity.Registration;
import com.registration.core.entity.Subject;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.registration.db.RegistrationRepository;
import com.registration.core.usecase.student.db.StudentRepository;
import com.registration.core.usecase.subject.AddSubject;
import com.registration.core.usecase.subject.DeleteSubject;
import com.registration.core.usecase.subject.UpdateSubject;
import com.registration.core.usecase.subject.db.SubjectRepository;
import com.registration.rest.bootstrap.RepoFactory;
import com.registration.rest.resource.AbstractResource;
import com.registration.rest.resource.student.StudentResourceDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(basePath="/registration/rest")
@Api(value = "classes")
@Path("/classes")
@Produces({"application/json"})
@Consumes({"application/json"})
public class SubjectResource extends AbstractResource {
	
	  @GET
	  public Response getAll(  ) {
		SubjectRepository repository = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
	    
		List<SubjectResourceDTO> queryResult = repository.getAll()
														.stream()
														.map( subject -> SubjectResourceDTO.newInstance(subject))
														.collect(Collectors.toList());
		
		List<SubjectResourceDTO> response = filterSubjects(queryResult);
														
	    return Response.status(Status.OK).entity( response ).build();
	  }

	  @Path("/{code}")
	  @GET
	  public Response get( @PathParam("code") String code ) {
		  
		SubjectRepository repository = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
		Optional<Subject> subjectResult = repository.getByCode(NonEmptyString.valueOf(code));
	    
		SubjectResourceDTO response = SubjectResourceDTO.newInstance(subjectResult.get());
		
	    return Response.status(Status.OK).entity( response ).build();
	  }	
	  
	  @Path("/{code}/students")
	  @GET
	  public Response getStudents( @PathParam("code") String code ) {

		RegistrationRepository registrationRepo = (RegistrationRepository) RepoFactory.instance.make(RegistrationRepository.class);		
		StudentRepository studentRepo = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
		  
		List<Registration> registrations = registrationRepo.getBySubjectCode(NonEmptyString.valueOf(code));
		  
		List<StudentResourceDTO> queryResult = registrations
												.stream()
												.map( reg -> studentRepo.getById(reg.getStudentId()))
												.filter( opt -> opt.isPresent())
												.map( opt -> StudentResourceDTO.newInstance(opt.get()))
												.collect(Collectors.toList());
		
		List<StudentResourceDTO> response = filterStudents(queryResult);
		
	    return Response.status(Status.OK).entity( response ).build();
	  }	  
	  
	  @POST
	  public Response add( @Valid SubjectResourceDTO subjectDto ) {
		  
		SubjectRepository repository = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
	    UseCase<Subject> service = AddSubject.newInstance(subjectDto.toSubject(), repository);
	    service.execute();
	    
	    SubjectResourceDTO response = SubjectResourceDTO.newInstance(service.getResult().get());
	    
	    return Response.status(Status.CREATED).entity( response ).build();
	  }
	  
	  @Path("/{code}")
	  @PUT
	  public Response put( @Valid SubjectResourceDTO subjectDto, @PathParam("code") String code ) {
		  	subjectDto.setCode(code);
		  	
			SubjectRepository repository = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
		    UseCase<Boolean> service = UpdateSubject.newInstance(subjectDto.toSubject(), repository);
		    service.execute();
		    
		    Status status = service.getResult().get() ? Status.OK : Status.NOT_FOUND;
		    
		    return Response.status( status ).entity( status ).build();
	  }	  
	  
	  
	  @Path("/{code}")
	  @DELETE
	  public Response delete( @PathParam("code") String code ) {
		  
		SubjectRepository repository = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
	    UseCase<Subject> service = DeleteSubject.newInstance(NonEmptyString.valueOf(code), repository);
	    service.execute();
	    
	    SubjectResourceDTO response = SubjectResourceDTO.newInstance(service.getResult().get());
	    
	    return Response.status(Status.OK).entity( response ).build();
	  }  
}
