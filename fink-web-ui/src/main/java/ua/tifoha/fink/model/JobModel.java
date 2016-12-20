package ua.tifoha.fink.model;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;

import org.apache.commons.lang3.reflect.FieldUtils;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.quartz.jobs.NoOpJob;
import ua.tifoha.fink.quartz.job.JobData;
import ua.tifoha.fink.utils.BaseUtils;

public class JobModel extends AbstractModel {
	private JobDetail jobDetail;
	private JobKey key;
	private String description;
	private Class<? extends Job> jobClass = NoOpJob.class;
	private Map<String, Object> jobDataMap = new LinkedHashMap<>();
	private boolean durable;
	private boolean requestsRecovery;
	private Collection<SimpleTrigger> triggers = new LinkedHashSet<>();

	public JobModel(JobDetail jobDetail) {
		this.jobDetail = jobDetail;
		key = jobDetail.getKey();
		description = jobDetail.getDescription();
		jobClass = jobDetail.getJobClass();
		durable = jobDetail.isDurable();
		jobDataMap.putAll(jobDetail.getJobDataMap());
		requestsRecovery = jobDetail.requestsRecovery();
		fillJobDataMap(jobDetail);
	}

	private void fillJobDataMap(JobDetail jobDetail) {
		Class<? extends Job> clazz = jobDetail.getJobClass();
		FieldUtils.getFieldsListWithAnnotation(clazz, JobData.class).forEach(field -> {
			String key = field.getName();
			Object value = BaseUtils.getDefaultValue(field.getType());
			jobDataMap.putIfAbsent(key, value);
		});
	}

	public JobModel() {
	}

	public JobKey getKey() {
		return key;
	}

	public String getDescription() {
		return description;
	}

	public Class<? extends Job> getJobClass() {
		return jobClass;
	}

	public Map<String, Object> getJobDataMap() {
		return jobDataMap;
	}

	public boolean isDurable() {
		return durable;
	}

	public boolean isRequestsRecovery() {
		return requestsRecovery;
	}

	public Collection<SimpleTrigger> getTriggers() {
		return triggers;
	}

	public void setTriggers(Collection<SimpleTrigger> triggers) {
		this.triggers = triggers;
	}

	public void setKey(JobKey key) {
		this.key = key;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setJobClass(Class<? extends Job> jobClass) {
		this.jobClass = jobClass;
	}

	public void setJobDataMap(Map<String, Object> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}

	public void setDurable(boolean durable) {
		this.durable = durable;
	}

	public void setRequestsRecovery(boolean requestsRecovery) {
		this.requestsRecovery = requestsRecovery;
	}
}
