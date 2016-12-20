package ua.tifoha.fink.services;

import static org.quartz.impl.matchers.GroupMatcher.anyGroup;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AssignableTypeFilter;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {
    protected static Logger logger = LoggerFactory.getLogger(JobServiceImpl.class);
//    private Map<OldJob, ScheduledFuture<?>> activeJobs = new LinkedHashMap<>();

    @Autowired
    private Scheduler scheduler;
    private List<String> jobClassPackages = Arrays.asList("ua.tifoha.fink.quartz.job", "org.quartz.jobs", "class org.springframework.scheduling.quartz");
    private Collection<Class> jobClasses;

    public JobServiceImpl() {
    }

    protected Collection<Class> findAllJobClasses() {
        ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
        provider.addIncludeFilter(new AssignableTypeFilter(Job.class));

        //        Set<BeanDefinition> candidates = provider.findCandidateComponents("");
//        jobClasses = candidates.stream()
//                               .map(BeanDefinition::getBeanClassName)
//                               .map(JobServiceImpl::classByName)
//                               .filter(Objects::nonNull)
//                               .collect(Collectors.toSet());
        return jobClassPackages
                .stream()
                .map(provider::findCandidateComponents)
                .flatMap(Collection::stream)
                .map(BeanDefinition::getBeanClassName)
                .map(JobServiceImpl::classByName)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
    }

    @SuppressWarnings("unchecked")
    private static <T> Class<T> classByName(String className) {
        try {
            return (Class<T>) Class.forName(className);
        } catch (Throwable e) {
            logger.warn("Fail to get job class by name: {}", className);
//            throw new RuntimeException();
        }
        return null;
    }

    @Override
    public Collection<Trigger> getAllTriggers() {
        return exceptionWrapper(anyGroup(), scheduler::getTriggerKeys)
                .stream()
                .map(this::findTriggerByKey)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<SimpleTrigger> getTriggersOfJob(JobKey key) {
        return (Collection<SimpleTrigger>)exceptionWrapper(key, scheduler::getTriggersOfJob);
    }

    @Override
    public Collection<JobDetail> getAllJobDetails() {
        return exceptionWrapper(anyGroup(), scheduler::getJobKeys).stream()
                .map(key -> exceptionWrapper(key, scheduler::getJobDetail))
                .collect(Collectors.toList());
    }

    @Override
    public Trigger create(Trigger trigger) {
        try {
            scheduler.scheduleJob(trigger);
        } catch (SchedulerException e) {
            handleException(e);
        }
        return null;
    }

    @Override
    public Collection<Class> getJobClasses() {
        if (jobClasses == null) {
            jobClasses = new LinkedHashSet<>();
            jobClasses.addAll(findAllJobClasses());
        }
        return jobClasses;
    }

    private void handleException(Throwable e) {
        throw new RuntimeException(e);
    }

    @Override
    public Optional<Trigger> findTriggerByKey(TriggerKey key) {
        return Optional.ofNullable(exceptionWrapper(key, scheduler::getTrigger));
    }

    @Override
    public Optional<JobDetail> findJobByKey(JobKey key) {
        return Optional.ofNullable(exceptionWrapper(key, scheduler::getJobDetail));
    }

    @Override
    public boolean deleteJob(JobKey key) {
        return exceptionWrapper(key, scheduler::deleteJob);
    }

    @Override
    public void saveJob(JobDetail jobDetail) {
        try {
            scheduler.addJob(jobDetail, true);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Collection<String> getCalendarNames() {
        try {
            return scheduler.getCalendarNames();
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void saveTrigger(Trigger trigger) {
        try {
            if (scheduler.checkExists(trigger.getKey())) {
                scheduler.rescheduleJob(trigger.getKey(), trigger);
            } else {
                scheduler.scheduleJob(trigger);
            }
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteTrigger(TriggerKey triggerKey) {
        try {
            scheduler.unscheduleJob(triggerKey);
        } catch (SchedulerException e) {
            throw new RuntimeException(e);
        }
    }

    private <T, R> R exceptionWrapper(T value, ThrowableFunction<T, R> function) {
        try {
            return function.apply(value);
        } catch (Throwable e) {
            handleException(e);
        }
        return null;
    }

    private interface ThrowableFunction<T, R> {
        R apply(T t) throws Throwable;
    }
}
