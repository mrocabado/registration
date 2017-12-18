package com.registration.rest.resource.student;

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
import com.registration.core.entity.Student;
import com.registration.core.entity.type.NonEmptyString;
import com.registration.core.usecase.registration.AddRegistration;
import com.registration.core.usecase.registration.db.RegistrationRepository;
import com.registration.core.usecase.student.AddStudent;
import com.registration.core.usecase.student.DeleteStudent;
import com.registration.core.usecase.student.UpdateStudent;
import com.registration.core.usecase.student.db.StudentRepository;
import com.registration.core.usecase.subject.db.SubjectRepository;
import com.registration.rest.bootstrap.RepoFactory;
import com.registration.rest.resource.AbstractResource;
import com.registration.rest.resource.registration.RegistrationDTO;
import com.registration.rest.resource.subject.SubjectResourceDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;

@SwaggerDefinition(basePath="/registration/rest")
@Api(value = "students")
@Path("/students")
@Produces({"application/json"})
@Consumes({"application/json"})
public class StudentResource extends AbstractResource {
	
	  @GET
	  public Response getAll( ) {
		StudentRepository repository = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
	    
		List<StudentResourceDTO> queryResult = repository.getAll()
														.stream()
														.map( student -> StudentResourceDTO.newInstance(student))
														.collect(Collectors.toList());
		
		List<StudentResourceDTO> response = filterStudents(queryResult);
														
	    return Response.status(Status.OK).entity( response ).build();
	  }
	  
	  @Path("/{id}")
	  @GET
	  public Response get( @PathParam("id") String id ) {
		  
		StudentRepository repository = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
		Optional<Student> studentResult = repository.getById(NonEmptyString.valueOf(id));
	    
		StudentResourceDTO response = StudentResourceDTO.newInstance(studentResult.get());
		
	    return Response.status(Status.OK).entity( response ).build();
	  }
	  
	  @Path("/{id}/subjects")
	  @GET
	  public Response getSubjects( @PathParam("id") String id ) {

		RegistrationRepository registrationRepo = (RegistrationRepository) RepoFactory.instance.make(RegistrationRepository.class);		
		SubjectRepository subjectRepo = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
		  
		List<Registration> registrations = registrationRepo.getByStudentId(NonEmptyString.valueOf(id));
		  
		List<SubjectResourceDTO> queryResult = registrations
												.stream()
												.map( reg -> subjectRepo.getByCode(reg.getSubjectCode()) )
												.filter( opt -> opt.isPresent() )
												.map( opt -> SubjectResourceDTO.newInstance(opt.get()))
												.collect(Collectors.toList());
		
		List<SubjectResourceDTO> response = filterSubjects(queryResult);
		
	    return Response.status(Status.OK).entity( response ).build();
	  }	  

	  @POST
	  public Response add( @Valid StudentResourceDTO studentDto ) {
		  
		StudentRepository repository = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
	    UseCase<Student> service = AddStudent.newInstance(studentDto.toStudent(), repository);
	    service.execute();
	    
	    StudentResourceDTO response = StudentResourceDTO.newInstance(service.getResult().get());
	    
	    return Response.status(Status.CREATED).entity( response ).build();
	  }
	  
	  @Path("/{id}")
	  @PUT
	  public Response put( @Valid StudentResourceDTO studentDto, @PathParam("id") String id ) {
		  	studentDto.setId(id);
		  	
		  	StudentRepository repository = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
		    UseCase<Boolean> service = UpdateStudent.newInstance(studentDto.toStudent(), repository);
		    service.execute();
		    
		    Status status = service.getResult().get() ? Status.OK : Status.NOT_FOUND;
		    
		    return Response.status( status ).entity( status ).build();
	  }	  
	  
	  
	  @Path("/{id}")
	  @DELETE
	  public Response delete( @PathParam("id") String id ) {
		  
		StudentRepository repository = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
	    UseCase<Student> service = DeleteStudent.newInstance(NonEmptyString.valueOf(id), repository);
	    service.execute();
	    
	    StudentResourceDTO response = StudentResourceDTO.newInstance(service.getResult().get());
	    
	    return Response.status(Status.OK).entity( response ).build();
	  }
	  
	  @Path("/{id}/register")
	  @POST
	  public Response register( List<String> subjectCodes, @PathParam("id") String id ) {
		
		RegistrationRepository registrationRepo = (RegistrationRepository) RepoFactory.instance.make(RegistrationRepository.class);		
		StudentRepository studentRepo = (StudentRepository) RepoFactory.instance.make(StudentRepository.class);
		SubjectRepository subjectRepo = (SubjectRepository) RepoFactory.instance.make(SubjectRepository.class);
		
	    UseCase<List<Registration>> service = AddRegistration.newInstance(NonEmptyString.valueOf(id), subjectCodes, registrationRepo, studentRepo, subjectRepo );
	    service.execute();
	    
		List<RegistrationDTO> response = service.getResult().get()
											.stream()
											.map( reg -> RegistrationDTO.newInstance(reg))
											.collect(Collectors.toList());
	    
	    return Response.status(Status.OK).entity( response ).build();
	  }
}
