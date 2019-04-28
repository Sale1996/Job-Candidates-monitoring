package com.intens.test.task.jobCandidate;

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

import com.intens.test.task.jobCandidate.dto.JobCandidateDTO;
import com.intens.test.task.jobCandidate.model.JobCandidate;
import com.intens.test.task.jobCandidate.repository.JobCandidateRepository;
import com.intens.test.task.jobCandidate.service.JobCandidateServiceImpl;
import com.intens.test.task.utils.DTOJobCandidateConverter;

@RunWith(SpringRunner.class)
@SpringBootTest
public class JobCandidateServiceTest {

	
	@Mock
	private JobCandidateRepository jobCandidateRepository;
	
	@Mock
	private DTOJobCandidateConverter jobCandidateConverter;
	
	@Mock
	private JobCandidate jobCandidate;
	
	@InjectMocks
	private JobCandidateServiceImpl jobCandidateService;
	
	
	@Test
	public void testFindAll() {
		
		when(jobCandidateRepository.findAll()).thenReturn(Arrays.asList(new JobCandidate()));
		when(jobCandidateConverter.convertToDTO(jobCandidate)).thenReturn(new JobCandidateDTO());
		List<JobCandidateDTO> candidates = jobCandidateService.findAll();
		assertThat(candidates).hasSize(1);
		
		
		verify(jobCandidateRepository, times(1)).findAll();
        verifyNoMoreInteractions(jobCandidateRepository);
        
	}
	
	
	@Test
	public void testfindOneById() {
		
		JobCandidateDTO dto = new JobCandidateDTO();
		dto.setId(5l);
		
		when(jobCandidateRepository.findById(2l)).thenReturn(Optional.of(jobCandidate));
		when(jobCandidateConverter.convertToDTO(jobCandidate)).thenReturn(dto);
		JobCandidateDTO foundObject = jobCandidateService.findById(2l);
	
		assertEquals(foundObject,dto);
        verify(jobCandidateRepository, times(1)).findById(2l);
        verifyNoMoreInteractions(jobCandidateRepository);

	}
	
	@Test
	@Transactional
	@Rollback(true)
	public void testSave() {
		
		when(jobCandidateRepository.findAll()).thenReturn(Arrays.asList(new JobCandidate()));
		
		JobCandidate bean = new JobCandidate();
		bean.setId(2l);
		
		
		when(jobCandidateRepository.save(bean)).thenReturn(bean);
		when(jobCandidateConverter.convertToDTO(jobCandidate)).thenReturn(new JobCandidateDTO());

		
		int dbSizeBeforeAdd = jobCandidateService.findAll().size();
		
		JobCandidateDTO dtoToSave = new JobCandidateDTO();
		dtoToSave.setEmail("test@gmail.com");
		dtoToSave.setTelephoneNumber("060/1231-231");
		dtoToSave.setName("testtt");
		
		
		when(jobCandidateConverter.convertFromDTO(dtoToSave)).thenReturn(bean);
		
		JobCandidateDTO dbBean = jobCandidateService.save(dtoToSave);
		assertThat(dbBean.getId()).isNotNull();
		
		when(jobCandidateRepository.findAll()).thenReturn(Arrays.asList(new JobCandidate(), bean));
		when(jobCandidateConverter.convertToDTO(jobCandidate)).thenReturn(new JobCandidateDTO());

		List<JobCandidateDTO> beans = jobCandidateService.findAll();
        assertThat(beans).hasSize(dbSizeBeforeAdd + 1);
        
        
        verify(jobCandidateRepository, times(2)).findAll();
        verify(jobCandidateRepository, times(1)).save(bean);
							
	}
	
}
