package com.intens.test.task.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intens.test.task.jobCandidate.dto.JobCandidateDTO;
import com.intens.test.task.jobCandidate.model.JobCandidate;
import com.intens.test.task.jobCandidate.repository.JobCandidateRepository;
import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.model.Skill;

@Component
public class DTOJobCandidateConverter {

	@Autowired
	public JobCandidateRepository jobCandidateRepository;
	
	@Autowired 
	public DTOSkillConverter skillConverter;
	
	
	
	public JobCandidateDTO convertToDTO (JobCandidate jobCandidate) {
		
		JobCandidateDTO dto = new JobCandidateDTO();
		
		dto.setId(jobCandidate.getId());
		dto.setName(jobCandidate.getName());
		dto.setDateOfBirth(jobCandidate.getDateOfBirth());
		dto.setTelephoneNumber(jobCandidate.getTelephoneNumber());
		dto.setEmail(jobCandidate.getEmail());
		
		for(Skill skill : jobCandidate.getSkills()) {
			dto.getSkills().add(skillConverter.convertToDTO(skill));
		}
		
		return dto;
		
	}
	
	public JobCandidate convertFromDTO( JobCandidateDTO dto ) {
		
		Optional<JobCandidate> jobCandidate = jobCandidateRepository.findById(dto.getId());
		
		if(jobCandidate.isPresent()) {
			
			return jobCandidate.get();
			
		}
		
		JobCandidate newCandidate = new JobCandidate();
		
		newCandidate.setId(dto.getId());
		newCandidate.setName(dto.getName());
		newCandidate.setDateOfBirth(dto.getDateOfBirth());
		newCandidate.setTelephoneNumber(dto.getTelephoneNumber());
		newCandidate.setEmail(dto.getEmail());
		
		for(SkillDTO skill : dto.getSkills()) {
			newCandidate.getSkills().add(skillConverter.convertFromDTO(skill));
		}	
		
		return newCandidate;
		
	}
	
	
}
