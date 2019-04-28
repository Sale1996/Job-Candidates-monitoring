package com.intens.test.task.utils;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.model.Skill;
import com.intens.test.task.skill.repository.SkillRepository;

@Component
public class DTOSkillConverter {

	@Autowired
	SkillRepository skillRepository;
	
	public SkillDTO convertToDTO (Skill skill) {
		
		SkillDTO dto = new SkillDTO();
		
		dto.setId(skill.getId());
		dto.setName(skill.getName());
		
		return dto;
		
	}
	
	public Skill convertFromDTO (SkillDTO dto) {
		
		Optional<Skill> skill  = skillRepository.findById(dto.getId());
		
		if(skill.isPresent()) {
			
			return skill.get();
			
		}
		
		Skill newSkill = new Skill();
		
		newSkill.setId(dto.getId());
		newSkill.setName(dto.getName());
		
		return newSkill;	
	}
}

