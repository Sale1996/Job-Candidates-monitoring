package com.intens.test.task.skill.controller;

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

import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.service.ISkillService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/skill")
@Api(value="skill")
@CrossOrigin(origins = "http://localhost:4200")
public class SkillController {

	@Autowired
	ISkillService skillService;
	
	
	
	@GetMapping("/")
	@ApiOperation( value = "Returns all skills", httpMethod = "GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message ="OK"),
							 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< List<SkillDTO> > getAllSkills (){
		
		List< SkillDTO > skills = skillService.findAll();
		
		return ( !skills.isEmpty() )? new ResponseEntity< List<SkillDTO> > ( skills, HttpStatus.OK ) : new ResponseEntity< List<SkillDTO> > ( HttpStatus.NOT_FOUND );
		
	}
	
	
	@GetMapping("/{id}")
	@ApiOperation( value = "Finds one skill by id.", notes = "Returns found skill.", httpMethod="GET")
	@ApiResponses( value = { @ApiResponse( code = 200, message = "OK"),
							 @ApiResponse( code = 404, message = "Not Found")})
	public ResponseEntity< SkillDTO > getOneSkillById (@PathVariable("id") Long id){
		
		SkillDTO skillDto = skillService.findById(id);
		
		return ( skillDto.getId()!=null)? new ResponseEntity< SkillDTO > ( skillDto, HttpStatus.OK ) : new ResponseEntity< SkillDTO > ( HttpStatus.NOT_FOUND );
		
	}
	
	
	@PostMapping("/")
	@ApiOperation( value = "Create a skill.", notes = "Returns the skill being saved.", httpMethod="POST", produces = "application/json", consumes = "application/json" )
	@ApiResponses( value = {
					@ApiResponse( code = 201 , message = "Created"),
					@ApiResponse( code = 400, message= "Bad request")
	})
	public ResponseEntity< SkillDTO > addSkill ( @RequestBody SkillDTO dto ){
			
		SkillDTO savedSkill = skillService.save(dto);
		
		return ( savedSkill!=null )? new ResponseEntity< SkillDTO > ( savedSkill, HttpStatus.CREATED ) : new ResponseEntity< SkillDTO > ( HttpStatus.BAD_REQUEST );

	}
	
	
	
	@PutMapping("/{id}")
	@ApiOperation( value= "Change a skill", notes = "Returns the skill being changed", httpMethod="PUT")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 400, message ="Bad Request")})
	public ResponseEntity< SkillDTO > updateSkill (@PathVariable("id") Long id, @RequestBody SkillDTO skillDto ){
			
		SkillDTO skillToUpdate = skillService.update(id, skillDto);
		
	    return ( skillToUpdate.getId() != null )? new ResponseEntity< SkillDTO > ( skillToUpdate, HttpStatus.OK ) : new ResponseEntity< SkillDTO > ( HttpStatus.BAD_REQUEST );

	}
	
	
	@DeleteMapping("/{id}")
	@ApiOperation( value = "Delete a skill.", notes = "Returns the skill being deleted", httpMethod="DELETE")
	@ApiResponses( value = { 
			 @ApiResponse( code = 200, message ="OK"),
			 @ApiResponse( code = 404, message ="Not Found")})	
	public ResponseEntity< SkillDTO > deleteSkill (@PathVariable("id") Long id){
	
		SkillDTO deletedSkillDTO = skillService.delete(id);
		
		return (deletedSkillDTO.getId() != null ) ? new ResponseEntity< SkillDTO > ( deletedSkillDTO,HttpStatus.OK ) : new ResponseEntity< SkillDTO > ( HttpStatus.NOT_FOUND );

	}
	
}
