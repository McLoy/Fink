package ua.tifoha.fink.controllers;

import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.quartz.JobDataMap;
import org.quartz.JobKey;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import ua.tifoha.fink.exceptions.NotFoundException;
import ua.tifoha.fink.model.MisfireInstruction;
import ua.tifoha.fink.model.Priority;
import ua.tifoha.fink.model.TriggerModel;
import ua.tifoha.fink.quartz.PersistentTrigger;
import ua.tifoha.fink.services.JobService;

@Controller
@RequestMapping ("job/{jobKey}/trigger")
public class TriggerController {
	@Autowired
	private JobService jobService;

	@RequestMapping (params = "form")
	public ModelAndView getNewForm(@PathVariable ("jobKey") Optional<JobKey> jobKey) {
		TriggerModel model = new TriggerModel();

		model.setJob(jobKey
				.flatMap(jobService::findJobByKey)
				.orElseThrow(NotFoundException::new));

		return getTriggerModel(model);
	}

	@RequestMapping (value = "/{key}", method = GET, params = "form")
	public ModelAndView getEditForm(@PathVariable ("jobKey") Optional<JobKey> jobKey,
									@PathVariable ("key") Optional<TriggerKey> key) {
		TriggerModel model = key
				.flatMap(jobService::findTriggerByKey)
				.map(TriggerModel::new)
				.orElseThrow(NotFoundException::new);


		model.setJob(jobKey
				.flatMap(jobService::findJobByKey)
				.orElseThrow(NotFoundException::new));

		return getTriggerModel(model);
	}

	@RequestMapping (value = "/{key}", method = POST)
	@SuppressWarnings ("unchecked")
	public ModelAndView save(@PathVariable ("jobKey") Optional<JobKey> jobKey,
							 @PathVariable ("key") Optional<TriggerKey> key,
							 @ModelAttribute ("trigger") TriggerModel model) {
		TriggerBuilder<Trigger> triggerBuilder = key
				.flatMap(jobService::findTriggerByKey)
				.map(t -> (TriggerBuilder<Trigger>) t.getTriggerBuilder())
				.orElseGet(TriggerBuilder::newTrigger);

		SimpleScheduleBuilder schedBuilder = simpleSchedule();

		if (model.getRepeatCount() == null) {
			schedBuilder.repeatForever();
		}

		schedBuilder
				.withIntervalInMilliseconds(model.getTimeUnit().toMillis(model.getInterval()));
		MisfireInstruction misfireInstruction = MisfireInstruction.getByValue(model.getMisfireInstruction());

		switch (misfireInstruction) {
			case FIRE_NOW:
				schedBuilder.withMisfireHandlingInstructionFireNow();
				break;
			case RESCHEDULE_NOW_WITH_EXISTING_REPEAT_COUNT:
				schedBuilder.withMisfireHandlingInstructionNowWithExistingCount();
				break;
			case RESCHEDULE_NOW_WITH_REMAINING_REPEAT_COUNT:
				schedBuilder.withMisfireHandlingInstructionNowWithExistingCount();
				break;
			case RESCHEDULE_NEXT_WITH_REMAINING_COUNT:
				schedBuilder.withMisfireHandlingInstructionNextWithRemainingCount();
				break;
			case RESCHEDULE_NEXT_WITH_EXISTING_COUNT:
				schedBuilder.withMisfireHandlingInstructionNowWithExistingCount();
				break;
			default:
				schedBuilder.withMisfireHandlingInstructionFireNow();
		}

		Trigger trigger = triggerBuilder
				.forJob(jobKey.orElseThrow(NotFoundException::new))
				.withDescription(model.getDescription())
				.withPriority(model.getPriority())
				.modifiedByCalendar(model.getCalendarName())
				.startAt(model.getStartTime())
				.endAt(model.getEndTime())
				.withSchedule(schedBuilder)
				.usingJobData(new JobDataMap(model.getJobDataMap()))
				.build();
		if (!(trigger instanceof PersistentTrigger)) {
			trigger = new PersistentTrigger((SimpleTrigger) trigger);
		}
		jobService.saveTrigger(trigger);

		return new ModelAndView("redirect:/job/" + jobKey.orElseThrow(NotFoundException::new) + "/trigger/" + trigger.getKey() + "?form");
	}


	@RequestMapping (value = "/{key}", method = DELETE)
	public void delete(@PathVariable ("jobKey") Optional<JobKey> jobKey,
					   @PathVariable ("key") Optional<TriggerKey> key,
					   HttpServletResponse response) {
		jobService.deleteTrigger(key.orElseThrow(NotFoundException::new));
	}


	private ModelAndView getTriggerModel(TriggerModel model) {
		ModelAndView view = new ModelAndView("trigger/edit");
		view.addObject("timeUnits", TimeUnit.values());
		view.addObject("entity", model);
		view.addObject("title", "Trigger");
		view.addObject("calendarNames", jobService.getCalendarNames());
		view.addObject("priorities", Priority.values());
		view.addObject("misfireInstructions", MisfireInstruction.values());
		return view;
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
}

