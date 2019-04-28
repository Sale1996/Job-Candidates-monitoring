package com.intens.test.task.skill.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.intens.test.task.skill.model.Skill;

@Repository
public interface SkillRepository extends JpaRepository< Skill , Long > {

	Optional< Skill > findByName (String name);

}
