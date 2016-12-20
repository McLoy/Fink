package ua.tifoha.fink.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.SimpleTrigger;
import org.quartz.impl.triggers.AbstractTrigger;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.OperableTrigger;

public class PersistentTrigger extends SimpleTriggerImpl{
    private boolean keepAlive = true;

    public PersistentTrigger() {
        super();
        keepAlive = true;
    }

    public PersistentTrigger(SimpleTrigger simpleTrigger) {
        this();
        setName(((AbstractTrigger)simpleTrigger).getName());
        setGroup(((AbstractTrigger)simpleTrigger).getGroup());
        setJobName(((AbstractTrigger)simpleTrigger).getJobName());
        setJobGroup(((AbstractTrigger)simpleTrigger).getJobGroup());
        setDescription(simpleTrigger.getDescription());
        setJobDataMap(simpleTrigger.getJobDataMap());
        setCalendarName(simpleTrigger.getCalendarName());
        setFireInstanceId(((OperableTrigger)simpleTrigger).getFireInstanceId());
        setMisfireInstruction(simpleTrigger.getMisfireInstruction());
        setPriority(simpleTrigger.getPriority());
        setKey(simpleTrigger.getKey());
        setStartTime(simpleTrigger.getStartTime());
        setEndTime(simpleTrigger.getEndTime());
        setNextFireTime(simpleTrigger.getNextFireTime());
        setPreviousFireTime(simpleTrigger.getPreviousFireTime());
        setRepeatCount(simpleTrigger.getRepeatCount());
        setRepeatInterval(simpleTrigger.getRepeatInterval());
        setTimesTriggered(simpleTrigger.getTimesTriggered());
    }

    public boolean isKeepAlive() {
        return keepAlive;
    }

    public void setKeepAlive(boolean keepAlive) {
        this.keepAlive = keepAlive;
    }

    @Override
    public CompletedExecutionInstruction executionComplete(JobExecutionContext context, JobExecutionException result) {
        CompletedExecutionInstruction e = super.executionComplete(context, result);

        if (e == CompletedExecutionInstruction.DELETE_TRIGGER && keepAlive) {
            return CompletedExecutionInstruction.SET_TRIGGER_COMPLETE;
        }

        return e;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
