package ua.tifoha.fink.model;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.springframework.format.annotation.DateTimeFormat;

public class TriggerModel extends AbstractModel{
	private TriggerKey key;
	private JobKey jobKey;
	private JobDetail job;
	private String description;
	private String calendarName;
	private int priority = Trigger.DEFAULT_PRIORITY;
	@DateTimeFormat (pattern = "dd.MM.yyyy HH:mm:ss")
	private Date startTime = new Date();
	@DateTimeFormat (pattern = "dd.MM.yyyy HH:mm:ss")
	private Date endTime;
	private int misfireInstruction = SimpleTrigger.MISFIRE_INSTRUCTION_SMART_POLICY;
	private Date nextFireTime;
	private Date previousFireTime;
	private Date finalFireTime;
	private long interval;
	private TimeUnit timeUnit = TimeUnit.SECONDS;
	private Integer repeatCount;
	private Map<String, Object> jobDataMap = new LinkedHashMap<>();


	public TriggerModel() {
	}

	public TriggerModel(Trigger trigger) {
//		this.trigger = trigger;
		key = trigger.getKey();
		jobKey = trigger.getJobKey();
		description = trigger.getDescription();
		calendarName = trigger.getCalendarName();
		priority = trigger.getPriority();
		startTime = trigger.getStartTime();
		endTime = trigger.getEndTime();
		misfireInstruction = trigger.getMisfireInstruction();
		nextFireTime = trigger.getNextFireTime();
		previousFireTime = trigger.getPreviousFireTime();
		finalFireTime = trigger.getFinalFireTime();
		if (trigger instanceof SimpleTrigger) {
			SimpleTrigger simpleTrigger = (SimpleTrigger) trigger;
			repeatCount = simpleTrigger.getRepeatCount() - simpleTrigger.getTimesTriggered();
			interval = timeUnit.convert(simpleTrigger.getRepeatInterval(), TimeUnit.MILLISECONDS);
		}
		jobDataMap = trigger.getJobDataMap();
	}

	public TriggerKey getKey() {
		return key;
	}

	public JobKey getJobKey() {
		return jobKey;
	}

	public String getDescription() {
		return description;
	}

	public String getCalendarName() {
		return calendarName;
	}

	public int getPriority() {
		return priority;
	}

	public Date getStartTime() {
		return startTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public Date getNextFireTime() {
		return nextFireTime;
	}

	public Date getPreviousFireTime() {
		return previousFireTime;
	}

	public Date getFinalFireTime() {
		return finalFireTime;
	}

	public int getMisfireInstruction() {
		return misfireInstruction;
	}

	public void setKey(TriggerKey key) {
		this.key = key;
	}

	public void setJobKey(JobKey jobKey) {
		this.jobKey = jobKey;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCalendarName(String calendarName) {
		this.calendarName = calendarName;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setMisfireInstruction(int misfireInstruction) {
		this.misfireInstruction = misfireInstruction;
	}

	public void setNextFireTime(Date nextFireTime) {
		this.nextFireTime = nextFireTime;
	}

	public void setPreviousFireTime(Date previousFireTime) {
		this.previousFireTime = previousFireTime;
	}

	public void setFinalFireTime(Date finalFireTime) {
		this.finalFireTime = finalFireTime;
	}

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}

	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public JobDetail getJob() {
		return job;
	}

	public void setJob(JobDetail job) {
		this.job = job;
	}

	public Map<String, Object> getJobDataMap() {
		return jobDataMap;
	}

	public void setJobDataMap(Map<String, Object> jobDataMap) {
		this.jobDataMap = jobDataMap;
	}
}
