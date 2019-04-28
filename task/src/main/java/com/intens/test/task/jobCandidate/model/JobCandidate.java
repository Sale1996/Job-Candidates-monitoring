package com.intens.test.task.jobCandidate.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.intens.test.task.skill.model.Skill;

import lombok.Data;

@Entity
@Table( name= "job_candidates" )
@Data
public class JobCandidate {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.IDENTITY )
	@Column ( name = "id" )
	private Long id;
	
	@Column ( name="name", nullable = false )
	private String name;
	
	@Column ( name = "email", nullable = false )
	private String email;
	
	@Column ( name = "telephone_number", nullable = false )
	private String telephoneNumber;
	
	@Column ( name= "date_of_birth", nullable = false )
	private LocalDate dateOfBirth;
	
	
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "candidate_skills",
            joinColumns = { @JoinColumn( name = "job_candidate_id" ) },
            inverseJoinColumns = { @JoinColumn( name = "skill_id" ) })
    private List<Skill> skills = new ArrayList<Skill>();

}
