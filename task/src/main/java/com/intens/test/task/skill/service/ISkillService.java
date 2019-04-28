package com.intens.test.task.skill.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intens.test.task.skill.dto.SkillDTO;

@Service
public interface ISkillService {
	
	
	public List< SkillDTO > findAll ();
	
	public SkillDTO findById (Long id);
	
	public SkillDTO save (SkillDTO skillToSave);
	
	public SkillDTO delete (Long id);
	
	public SkillDTO update (Long id, SkillDTO updatedSkill);
	
	

}
