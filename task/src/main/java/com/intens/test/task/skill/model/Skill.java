package com.intens.test.task.skill.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.intens.test.task.jobCandidate.model.JobCandidate;

import lombok.Data;

@Entity
@Table (name="skills")
@Data
public class Skill {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@Column ( name = "id" )
	private Long id;
	
	@Column ( name="name", nullable = false )
	private String name;
	
	@ManyToMany ( fetch = FetchType.LAZY, mappedBy = "skills" )
    private List<JobCandidate> posts = new ArrayList<JobCandidate>();
}
