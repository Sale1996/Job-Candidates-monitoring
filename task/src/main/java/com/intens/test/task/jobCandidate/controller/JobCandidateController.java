package com.intens.test.task.jobCandidate.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.intens.test.task.jobCandidate.dto.JobCandidateDTO;
import com.intens.test.task.jobCandidate.service.IJobCandidateService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping( "/api/jobcandidate" )
@Api( value="jobCandidate" )
@CrossOrigin(origins = "http://localhost:4200")
public class JobCandidateController {

	@Autowired
	IJobCandidateService candidateService;
	
	
	
	@GetMapping("/")
	@ApiOperation( value = "Returns all job candidates", httpMethod = "GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message ="OK"),
							 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< List<JobCandidateDTO> > getAllJobCandidates(){
		
		List< JobCandidateDTO > jobCandidates = candidateService.findAll();
		
		return ( !jobCandidates.isEmpty() )? new ResponseEntity< List<JobCandidateDTO> > ( jobCandidates, HttpStatus.OK ) : new ResponseEntity< List<JobCandidateDTO> >( HttpStatus.NOT_FOUND );
	}
	
	@GetMapping("/name/{name}")
	@ApiOperation( value = "Returns all job candidates by name", httpMethod = "GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message ="OK"),
							 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< List<JobCandidateDTO> > getAllJobCandidatesByName ( @PathVariable("name") String name ){
		
		List< JobCandidateDTO > jobCandidates = candidateService.findAllByName(name);
		
		return ( !jobCandidates.isEmpty() )? new ResponseEntity< List<JobCandidateDTO> > ( jobCandidates, HttpStatus.OK ) : new ResponseEntity< List<JobCandidateDTO> > ( HttpStatus.NOT_FOUND );
		
	}
	
	@PostMapping("/skill/")
	@ApiOperation( value = "Returns all job candidates by skill", httpMethod = "GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message ="OK"),
							 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< List<JobCandidateDTO> > getAllJobCandidatesBySkill ( @RequestBody List<Long> ids ){
		
		List< JobCandidateDTO > jobCandidates = candidateService.findAllBySkill(ids);
		
		return ( !jobCandidates.isEmpty() )? new ResponseEntity< List<JobCandidateDTO> > ( jobCandidates, HttpStatus.OK ) : new ResponseEntity< List<JobCandidateDTO> > ( HttpStatus.NOT_FOUND );
		
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation( value = "Finds one job candidate by id.", notes = "Returns found candidate.", httpMethod="GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message = "OK"),
							 @ApiResponse( code = 404, message = "Not Found")})
	public ResponseEntity< JobCandidateDTO > getOneJobCandidateById ( @PathVariable("id") Long id ){
		
		JobCandidateDTO candidateDto = candidateService.findById(id);
		
		return ( candidateDto.getId()!=null )? new ResponseEntity< JobCandidateDTO > ( candidateDto, HttpStatus.OK ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.NOT_FOUND );
		
	}
	
	
	@PostMapping("/")
	@ApiOperation( value = "Create a job candidate.", notes = "Returns the candidate being saved.", httpMethod="POST", produces = "application/json", consumes = "application/json" )
	@ApiResponses( value = {
					@ApiResponse( code = 201 , message = "Created"),
					@ApiResponse( code = 400, message= "Bad request")
	})
	public ResponseEntity< JobCandidateDTO > addJobCandidate ( @RequestBody JobCandidateDTO dto ){
			
		JobCandidateDTO savedCandidate = candidateService.save(dto);
		
		return ( savedCandidate!=null )? new ResponseEntity< JobCandidateDTO > ( savedCandidate, HttpStatus.CREATED ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.BAD_REQUEST );

	}
	
	
	
	@PutMapping("/{id}")
	@ApiOperation( value= "Change a job candidate", notes = "Returns the job candidate being changed", httpMethod="PUT")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 400, message ="Bad Request")})
	public ResponseEntity< JobCandidateDTO > updateCandidate ( @PathVariable("id") Long id, @RequestBody JobCandidateDTO candidateDto ){
			
		JobCandidateDTO candidateToUpdate = candidateService.update ( id, candidateDto );
		
	    return ( candidateToUpdate.getId() != null )? new ResponseEntity< JobCandidateDTO > ( candidateToUpdate, HttpStatus.OK ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.BAD_REQUEST );

	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation( value = "Delete a job candidate.", notes = "Returns the job candidate being deleted", httpMethod="DELETE")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< JobCandidateDTO > deleteSkill ( @PathVariable("id") Long id ){
	
		JobCandidateDTO deletedCandidateDTO = candidateService.delete(id);
		
		return ( deletedCandidateDTO.getId() != null ) ? new ResponseEntity< JobCandidateDTO > ( deletedCandidateDTO, HttpStatus.OK ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.NOT_FOUND );

	}
	
	
	
	@PutMapping("/addSkill/{candidateId}/{skillId}")
	@ApiOperation( value= "Add skill to candidate", notes = "Returns the job candidate being updated", httpMethod="PUT")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 400, message ="Bad Request")})
	public ResponseEntity< JobCandidateDTO > addSkill ( @PathVariable("candidateId") Long candidateId, @PathVariable("skillId") Long skillId ){
			
		JobCandidateDTO candidateToUpdate = candidateService.addSkill(candidateId, skillId);
		
	    return ( candidateToUpdate.getId() != null )? new ResponseEntity< JobCandidateDTO > ( candidateToUpdate, HttpStatus.OK ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.BAD_REQUEST );

	}	

	
	@PutMapping("/removeSkill/{candidateId}/{skillId}")
	@ApiOperation( value= "Add skill to candidate", notes = "Returns the job candidate being updated", httpMethod="PUT")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 400, message ="Bad Request")})
	public ResponseEntity< JobCandidateDTO > removeSkill ( @PathVariable("candidateId") Long candidateId, @PathVariable("skillId") Long skillId ){
			
		JobCandidateDTO candidateToUpdate = candidateService.removeSkill(candidateId, skillId);
		
	    return ( candidateToUpdate.getId() != null )? new ResponseEntity< JobCandidateDTO > ( candidateToUpdate, HttpStatus.OK ) : new ResponseEntity< JobCandidateDTO > ( HttpStatus.BAD_REQUEST );

	}	
}
