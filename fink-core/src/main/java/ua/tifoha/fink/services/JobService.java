package ua.tifoha.fink.services;

import java.util.Collection;
import java.util.Optional;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;

public interface JobService {
	Collection<Trigger> getAllTriggers();

	Collection<SimpleTrigger> getTriggersOfJob(JobKey key);

	Collection<JobDetail> getAllJobDetails();

	Trigger create(Trigger trigger);

	Collection<Class> getJobClasses();

	Optional<Trigger> findTriggerByKey(TriggerKey key);

	Optional<JobDetail> findJobByKey(JobKey key);

	boolean deleteJob(JobKey key);

	void saveJob(JobDetail jobDetail);

	Collection<String> getCalendarNames();

	void saveTrigger(Trigger trigger);

	void deleteTrigger(TriggerKey triggerKey);
}
