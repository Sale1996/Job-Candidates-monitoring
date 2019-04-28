package com.intens.test.task.jobCandidate.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.intens.test.task.jobCandidate.dto.JobCandidateDTO;

@Service
public interface IJobCandidateService {

	public List< JobCandidateDTO > findAll ();
	
	public List< JobCandidateDTO > findAllByName (String name);
	
	public List< JobCandidateDTO > findAllBySkill (List<Long> skillIds);
	
	public JobCandidateDTO findById (Long id);
	
	public JobCandidateDTO save (JobCandidateDTO candidateToSave);
	
	public JobCandidateDTO delete (Long id);
	
	public JobCandidateDTO update (Long id, JobCandidateDTO updatedCandidate);
	
	public JobCandidateDTO addSkill (Long candidateId, Long skillId);
	
	public JobCandidateDTO removeSkill (Long candidateId, Long skillId);
	
	
}
