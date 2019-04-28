 	package com.intens.test.task.skill.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.model.Skill;
import com.intens.test.task.skill.repository.SkillRepository;
import com.intens.test.task.utils.DTOSkillConverter;


@Component
public class SkillServiceImpl implements ISkillService {

	@Autowired
	SkillRepository skillRepository;
	
	@Autowired
	DTOSkillConverter skillConverter;
	
		
	
	public List< SkillDTO > findAll () {
		
		Optional< List<Skill> > skills = Optional.of( skillRepository.findAll() );
		
		ArrayList < SkillDTO > dtoSkills = new ArrayList<SkillDTO>();
		
		if ( skills.isPresent() ) {
			
			for ( Skill skill : skills.get() ) {
				
				dtoSkills.add(skillConverter.convertToDTO(skill));
				
			}
			
			return dtoSkills;
			
		}
			
		return Collections.emptyList();

		
	}

	
	public SkillDTO findById (Long id) {
		
		Optional< Skill > skill = skillRepository.findById(id);
		
		
		if ( skill.isPresent() ) {
			
			return skillConverter.convertToDTO(skill.get());
		
		}
		else {
			
			return new SkillDTO();
			
		}
		
	}

	
	public SkillDTO save (SkillDTO skillToSave) {
		
			//checking if there is already skill with the same name
		
			Optional< Skill > foundSkill = skillRepository.findByName(skillToSave.getName());
			
			if( foundSkill.isPresent() ) {
				
				return new SkillDTO();
			
			}
			
			skillToSave.setId(-1l);
				
			Skill skill = skillRepository.save(skillConverter.convertFromDTO(skillToSave));
			
			skillToSave.setId(skill.getId());
			
			return skillToSave;

	}

	
	public SkillDTO delete (Long id) {
		
		Optional< Skill > skillToDelete = skillRepository.findById(id);
		
		if( skillToDelete.isPresent() ) {
			
			skillRepository.deleteById(id);
			
			return skillConverter.convertToDTO(skillToDelete.get());
		
		}
		
		return new SkillDTO();
		
	}

	
	public SkillDTO update (Long id, SkillDTO updatedSkill) {
		
		Optional< Skill > skillForChange = skillRepository.findById(id);
		
		if( skillForChange.isPresent() && updatedSkill!=null ) {
			
			//checking if there is already skill with the same name but not same id
			
			Optional<Skill> foundSkill = skillRepository.findByName(updatedSkill.getName());
			
			if( foundSkill.isPresent() && foundSkill.get().getId() != skillForChange.get().getId() ) {
				
				return new SkillDTO();
			
			}
										
			skillForChange.get().setName(updatedSkill.getName());
	
			skillRepository.save(skillForChange.get());
			
			updatedSkill.setId(skillForChange.get().getId());
			
			
			return updatedSkill;
		
		}
		
		return new SkillDTO();
	}

}
