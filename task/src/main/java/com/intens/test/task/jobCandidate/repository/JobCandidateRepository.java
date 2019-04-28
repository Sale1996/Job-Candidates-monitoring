package com.intens.test.task.jobCandidate.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.intens.test.task.jobCandidate.model.JobCandidate;

@Repository
public interface JobCandidateRepository extends JpaRepository< JobCandidate, Long> {
	
	Optional< List<JobCandidate> > findAllByName (String name);

	Optional< JobCandidate > findByEmail (String email);

}
