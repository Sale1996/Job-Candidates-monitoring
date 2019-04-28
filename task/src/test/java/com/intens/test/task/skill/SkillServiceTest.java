package com.intens.test.task.skill;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import com.intens.test.task.skill.dto.SkillDTO;
import com.intens.test.task.skill.model.Skill;
import com.intens.test.task.skill.repository.SkillRepository;
import com.intens.test.task.skill.service.SkillServiceImpl;
import com.intens.test.task.utils.DTOSkillConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkillServiceTest {

	
	@Mock
	private SkillRepository skillRepository;
	
	@Mock
	private DTOSkillConverter skillConverter;
	
	@Mock
	private Skill skill;
	
	@InjectMocks
	private SkillServiceImpl skillService;
	
	
	@Test
	public void testFindAll() {
		
		when(skillRepository.findAll()).thenReturn(Arrays.asList(new Skill()));
		when(skillConverter.convertToDTO(skill)).thenReturn(new SkillDTO());
		List<SkillDTO> skills = skillService.findAll();
		assertThat(skills).hasSize(1);
		
		
		verify(skillRepository, times(1)).findAll();
        verifyNoMoreInteractions(skillRepository);
        
	}
	
	
	@Test
	public void testfindOneById() {
		
		SkillDTO dto = new SkillDTO();
		dto.setId(5l);
		
		when(skillRepository.findById(2l)).thenReturn(Optional.of(skill));
		when(skillConverter.convertToDTO(skill)).thenReturn(dto);
		SkillDTO foundObject = skillService.findById(2l);
	
		assertEquals(foundObject,dto);
        verify(skillRepository, times(1)).findById(2l);
        verifyNoMoreInteractions(skillRepository);

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSave() {
		
		when(skillRepository.findAll()).thenReturn(Arrays.asList(new Skill()));
		
		Skill bean = new Skill();
		bean.setId(2l);
		
		when(skillRepository.findByName(skill.getName())).thenReturn(Optional.empty());
		when(skillRepository.save(bean)).thenReturn(bean);
		when(skillConverter.convertToDTO(skill)).thenReturn(new SkillDTO());

		
		int dbSizeBeforeAdd = skillService.findAll().size();
		
		SkillDTO dtoToSave = new SkillDTO();
		
		when(skillConverter.convertFromDTO(dtoToSave)).thenReturn(bean);
		
		SkillDTO dbBean = skillService.save(dtoToSave);
		assertThat(dbBean.getId()).isNotNull();
		
		when(skillRepository.findAll()).thenReturn(Arrays.asList(new Skill(), bean));
		when(skillConverter.convertToDTO(skill)).thenReturn(new SkillDTO());

		List<SkillDTO> beans = skillService.findAll();
        assertThat(beans).hasSize(dbSizeBeforeAdd + 1);
        
        
        verify(skillRepository, times(2)).findAll();
        verify(skillRepository, times(1)).save(bean);
							
	}
	
}
