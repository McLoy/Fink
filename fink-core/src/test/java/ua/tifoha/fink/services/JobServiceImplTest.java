package ua.tifoha.fink.services;

import static org.junit.Assert.*;

import org.junit.Test;

public class JobServiceImplTest {
	@Test
	public void shouldfindJobClases() throws Exception {
		JobServiceImpl jobService = new JobServiceImpl();
		System.out.println(jobService.findAllJobClasses());
	}
}