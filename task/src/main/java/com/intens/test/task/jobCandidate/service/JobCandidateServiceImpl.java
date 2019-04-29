package com.intens.test.task.jobCandidate.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intens.test.task.jobCandidate.dto.JobCandidateDTO;
import com.intens.test.task.jobCandidate.model.JobCandidate;
import com.intens.test.task.jobCandidate.repository.JobCandidateRepository;
import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.model.Skill;
import com.intens.test.task.skill.repository.SkillRepository;
import com.intens.test.task.utils.DTOJobCandidateConverter;
import com.intens.test.task.utils.DTOSkillConverter;

@Component
public class JobCandidateServiceImpl implements IJobCandidateService {

	@Autowired
	JobCandidateRepository jobCandidateRepository;
	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	DTOJobCandidateConverter jobCandidateConverter;
	@Autowired
	DTOSkillConverter skillConverter;
	

	
	
	public List< JobCandidateDTO > findAll () {
		
		Optional< List<JobCandidate> > candidates = Optional.of ( jobCandidateRepository.findAll() );
		
		ArrayList < JobCandidateDTO > dtoCandidates = new ArrayList< JobCandidateDTO >();
		
		if ( candidates.isPresent() ) {
			
			for ( JobCandidate candidate : candidates.get() ) {
				
				dtoCandidates.add(jobCandidateConverter.convertToDTO(candidate));
				
			}
			
			return dtoCandidates;
			
		}
			
		return Collections.emptyList();

		
	}

	
	public List< JobCandidateDTO > findAllByName ( String name ) {
		
		Optional< List<JobCandidate> > candidates = jobCandidateRepository.findAllByNameContaining(name);
		
		ArrayList < JobCandidateDTO > dtoCandidates = new ArrayList< JobCandidateDTO >();
		
		if ( candidates.isPresent() ) {
			
			for ( JobCandidate candidate : candidates.get() ) {
				
				dtoCandidates.add(jobCandidateConverter.convertToDTO(candidate));
				
			}
			
			return dtoCandidates;
			
		}
			
		return Collections.emptyList();

		
	}

	
	public List< JobCandidateDTO > findAllBySkill (List<Long> skillId) {
		
		Optional< List<JobCandidate> > candidates = Optional.of(jobCandidateRepository.findAll());
	
		ArrayList < JobCandidateDTO > dtoCandidates = new ArrayList< JobCandidateDTO >();
		
		if ( candidates.isPresent() ) {
			
			for ( JobCandidate candidate : candidates.get() ) {
				
				boolean isValid = true;
		
				for( Long id : skillId ) {
					
					Optional< Skill > skill = skillRepository.findById(id);
					
					if( !skill.isPresent() ) {
						
						return Collections.emptyList();
					
					}
					
					if( !candidate.getSkills().contains( skill.get() ) ) {
						
						isValid=false;
						break;
						
					}
					
				}
				
				if(isValid) {
					
					dtoCandidates.add(jobCandidateConverter.convertToDTO(candidate));
					
				}
				
				
				
			}
			
			return dtoCandidates;
			
		}
		
		return Collections.emptyList();

		
	}

	
	public JobCandidateDTO findById (Long id) {
		
		Optional< JobCandidate > candidate = jobCandidateRepository.findById(id);
		
		
		if ( candidate.isPresent() ) {
			
			return jobCandidateConverter.convertToDTO(candidate.get());
		
		}
		else {
			
			return new JobCandidateDTO();
			
		}
		
	}

	
	public JobCandidateDTO save (JobCandidateDTO candidateToSave) {
		
		if( !isValidCandidate(candidateToSave) ) {
			
			return new JobCandidateDTO();
		
		}
		
		//Checking if there is already candidate with same email
		Optional<JobCandidate> candidate = jobCandidateRepository.findByEmail(candidateToSave.getEmail());
		
		if( candidate.isPresent() ) {
			
			return new JobCandidateDTO();
			
		}
		
		candidateToSave.setId(-1l);
		
		JobCandidate jobCandidate = jobCandidateRepository.save(jobCandidateConverter.convertFromDTO(candidateToSave));
		
		candidateToSave.setId(jobCandidate.getId());
		
		return candidateToSave;
	
	}

	



	public JobCandidateDTO delete (Long id) {
		
		Optional<JobCandidate> jobCandidateToDelete = jobCandidateRepository.findById(id);
		
		if( jobCandidateToDelete.isPresent() ) {
			
			jobCandidateRepository.deleteById(id);
			
			return jobCandidateConverter.convertToDTO(jobCandidateToDelete.get());
		
		}
		
		return new JobCandidateDTO();
		
	}

	
	public JobCandidateDTO update (Long id, JobCandidateDTO updatedCandidate) {
		
		Optional<JobCandidate> jobCandidateForChange = jobCandidateRepository.findById(id);
		
		if( jobCandidateForChange.isPresent() && updatedCandidate!=null ) {
			
			if( !isValidCandidate(updatedCandidate) ) {
				
				return new JobCandidateDTO();
			
			}
			
			//Checking if there is already candidate with same email and different id
			Optional<JobCandidate> candidate = jobCandidateRepository.findByEmail(updatedCandidate.getEmail());
			
			if( candidate.isPresent() && id != candidate.get().getId() ) {
				
				return new JobCandidateDTO();
				
			}
										
			jobCandidateForChange.get().setName(updatedCandidate.getName());
			jobCandidateForChange.get().setEmail(updatedCandidate.getEmail());
			jobCandidateForChange.get().setTelephoneNumber(updatedCandidate.getTelephoneNumber());
			jobCandidateForChange.get().setDateOfBirth(updatedCandidate.getDateOfBirth());
	
			jobCandidateRepository.save(jobCandidateForChange.get());
			
			updatedCandidate.setId(jobCandidateForChange.get().getId());
			
			
			return updatedCandidate;
		
		}
		
		return new JobCandidateDTO();
	}

	
	public JobCandidateDTO addSkill (Long candidateId, Long skillId) {
		
		Optional< JobCandidate > jobCandidateForChange = jobCandidateRepository.findById(candidateId);
		
		Optional< Skill > skill = skillRepository.findById(skillId);
		
		
		if( jobCandidateForChange.isPresent() && skill.isPresent() ) {
			//if user already has same skill, we wont add it twice
			if(jobCandidateForChange.get().getSkills().contains(skill.get())) {
				
				return new JobCandidateDTO();
				
			}
										
			jobCandidateForChange.get().getSkills().add(skill.get());
	
			jobCandidateRepository.save(jobCandidateForChange.get());
						
			return jobCandidateConverter.convertToDTO(jobCandidateForChange.get());
		
		}
		
		return new JobCandidateDTO();
	}

	
	public JobCandidateDTO removeSkill (Long candidateId, Long skillId) {
		
		Optional< JobCandidate > jobCandidateForChange = jobCandidateRepository.findById(candidateId);
		
		Optional< Skill > skill = skillRepository.findById(skillId);
		
		
		if( jobCandidateForChange.isPresent() && skill.isPresent() ) {
										
			jobCandidateForChange.get().getSkills().remove(skill.get());
	
			jobCandidateRepository.save(jobCandidateForChange.get());
						
			return jobCandidateConverter.convertToDTO(jobCandidateForChange.get());
		
		}
		
		return new JobCandidateDTO();
	}
	
	
	
	
	public boolean isValidCandidate(JobCandidateDTO candidateToSave) {
		
		boolean valid = true;
		
		//validating email
		Pattern emailRegex = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
		
		Matcher emailMatcher = emailRegex .matcher(candidateToSave.getEmail());
		if( !emailMatcher.find() ) {
			
			valid = false;
		
		}
		
         
        //validating telephone number
		Pattern telephoneNumberRegex = Pattern.compile("^[+]*[(]{0,1}[0-9]{1,4}[)]{0,1}[-\\s\\./0-9]*$", Pattern.CASE_INSENSITIVE);
		
		Matcher telephoneMatcher = telephoneNumberRegex .matcher(candidateToSave.getTelephoneNumber());
		if( !telephoneMatcher.find() ) {
			
			valid = false;
		
		} 
		
		//validating list of skills
		for( SkillDTO skill : candidateToSave.getSkills() ) {
			
			Optional<Skill> bean = skillRepository.findById(skill.getId());
			
			if( !bean.isPresent() ) {
				
				valid = false;
			
			}
			
		}
		
		return valid;
		
	}

}
