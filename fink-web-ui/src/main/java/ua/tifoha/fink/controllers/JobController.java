package ua.tifoha.fink.controllers;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.fink.exceptions.NotFoundException;
import ua.tifoha.fink.model.JobModel;
import ua.tifoha.fink.services.JobService;

@Controller
@RequestMapping ("job")
public class JobController {
	@Autowired
	private JobService jobService;

	@RequestMapping (method = GET)
	public ModelAndView getJobList() {
		ModelAndView model = new ModelAndView("job/list");
		List<JobModel> jobList = jobService.getAllJobDetails()
										   .stream()
										   .map(JobModel::new)
										   .peek(jobModel -> jobModel.setTriggers(jobService.getTriggersOfJob(jobModel.getKey())))
										   .collect(Collectors.toList());
		model.addObject("jobList", jobList);
		return model;
	}

	@RequestMapping (params = "form", method = GET)
	public ModelAndView getJobNewForm() {
		return getJobDetailModel(new JobModel());
	}

	@RequestMapping (value = "/{key}", method = GET, params = "form")
	public ModelAndView getJobEditForm(@PathVariable (value = "key") Optional<JobKey> key) {
		JobModel jobModel = key
				.flatMap(jobService::findJobByKey)
				.map(JobModel::new)
				.orElseThrow(NotFoundException::new);

		Collection<SimpleTrigger> triggers = jobService.getTriggersOfJob(key.orElseThrow(NotFoundException::new));
		jobModel.setTriggers(triggers);

		return getJobDetailModel(jobModel);
	}

	@RequestMapping (value = "/{key}", method = POST)
	public ModelAndView save(@PathVariable ("key") Optional<JobKey> key,
							 @ModelAttribute ("job") JobModel model) {
		JobBuilder jobBuilder = key.flatMap(jobService::findJobByKey)
								   .map(JobDetail::getJobBuilder)
								   .orElseGet(JobBuilder::newJob);


		JobDetail jobDetail = jobBuilder
				.ofType(model.getJobClass())
				.withDescription(model.getDescription())
				.usingJobData(new JobDataMap(model.getJobDataMap()))
				.storeDurably(model.isDurable())
				.requestRecovery(model.isRequestsRecovery())
				.build();

		jobService.saveJob(jobDetail);

		return new ModelAndView("redirect:/job/" + jobDetail.getKey() + "?form");
	}

	private ModelAndView getJobDetailModel(JobModel model) {
		ModelAndView view = new ModelAndView("job/edit");
		view.addObject("timeUnits", TimeUnit.values());
		view.addObject("entity", model);
		view.addObject("title", "Job");
		view.addObject("jobClasses", jobService.getJobClasses());
		return view;
	}


//	@RequestMapping (value = "/{key}/trigger/{triggerKey}", method = POST)
//	public ModelAndView saveTrigger(
//			@PathVariable("key") Optional<JobKey> key,
//			@ModelAttribute("trigger") TriggerModel triggerModel ) {
//
//		return null;
//	}

	@RequestMapping (value = "/{key}", method = DELETE)
	public void delete(@PathVariable ("key") Optional<JobKey> key,
					   HttpServletResponse response) {
		jobService.deleteJob(key.orElseThrow(NotFoundException::new));
	}

	//	@RequestMapping (value = "/json", method = GET)
//	@ResponseBody
//	public Collection<JobModel> getAllAsJson() {
//		JobDetail job = JobBuilder.newJob(App.HelloJob.class)
//								  .storeDurably(true)
//								  .withDescription("Dexctiopin")
//								  .usingJobData("phone", "+380971396134")
//								  .build();
//
//		return Collections.singleton(new JobModel(job));
//	}
//
//	@RequestMapping (value = "/json2", method = GET)
//	@ResponseBody
//	public Collection<PingJob> getAllAsJson1() {
//		PingJob job = new PingJob("http://google.com", 80, 50, 5);
//
//		return Collections.singleton(job);
//	}
//	@RequestMapping(value = "{key}/trigger")
//	class TriggerCtrl {
//		}
}

