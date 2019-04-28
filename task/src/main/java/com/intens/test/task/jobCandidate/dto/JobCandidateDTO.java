package com.intens.test.task.jobCandidate.dto;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.intens.test.task.skill.dto.SkillDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobCandidateDTO {

	private Long id;
	
	private String name;
	
	private String email;
	
	private String telephoneNumber;
	
	private LocalDate dateOfBirth;
	
	private List<SkillDTO> skills = new ArrayList<SkillDTO>();
	
}
