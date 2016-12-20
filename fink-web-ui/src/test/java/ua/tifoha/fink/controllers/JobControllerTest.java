package ua.tifoha.fink.controllers;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.anyObject;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.quartz.impl.JobDetailImpl;
import org.quartz.jobs.NoOpJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.ContextHierarchy;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.tifoha.fink.config.WebConfig;
import ua.tifoha.fink.quartz.job.AbstractPingJob;
import ua.tifoha.fink.services.JobService;

@RunWith (SpringJUnit4ClassRunner.class)
@ContextHierarchy ( {
		@ContextConfiguration (classes = {JobControllerTest.AppContext.class}),
		@ContextConfiguration (classes = WebConfig.class),
})

@WebAppConfiguration
public class JobControllerTest {
	public static final String EXIST_JOB_KEY = "DEFAULT:6da64b5bd2ee-19584599-a0d2-488b-93dd-e8fdde0c04ff";
	public static final String NOT_EXIST_JOB_KEY = "DEFAULT:NotExist";
	@Autowired
	private WebApplicationContext context;

	@Autowired
	private JobService jobService;

	private MockMvc mvc;

	@Before
	public void setup() {
		when(jobService.getJobClasses()).thenReturn(asList(AbstractPingJob.class, AbstractPingJob.class));
		mvc = MockMvcBuilders
				.webAppContextSetup(context)
//				.apply(springSecurity())
				.build();
	}

	@Test
	public void shouldGetEditFormForExistJobDetail() throws Exception {
		when(jobService.findJobByKey(anyObject())).thenReturn(Optional.of(new JobDetailImpl()));
		mvc.perform(get("/job/{key}", EXIST_JOB_KEY)
				.param("form", ""))
		   .andExpect(status().isOk());
	}

	@Test
	public void shouldReturnNotFoundForNoExistJobDetail() throws Exception {
		when(jobService.findJobByKey(anyObject())).thenReturn(Optional.empty());
		mvc.perform(get("/job/{key}", NOT_EXIST_JOB_KEY)
				.param("form", ""))
		   .andExpect(status().isNotFound());
	}

	@Test
	public void shouldGetNewForm() throws Exception {
		mvc.perform(get("/job").param("form", ""))
		   .andExpect(status().isOk());
	}

	@Test
	public void shouldCreateNewJobDetailWhenKeyIsNull() throws Exception {
		mvc.perform(post("/job/{key}", "null")
				.param("job.jobClass", NoOpJob.class.getName()))
		   .andExpect(status().isOk());
	}

	@Configuration
	public static class AppContext {
		@Bean
		public JobService jobService() {
			return Mockito.mock(JobService.class);
		}
	}
}