package ua.tifoha.fink.config;

import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.SchedulingConfiguration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import ua.tifoha.fink.quartz.AutowiredQuartzJobFactory;

import javax.sql.DataSource;
import java.util.Properties;

//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
@PropertySource("classpath:application.properties")
public class SchedulerConfig extends SchedulingConfiguration {

    @Value("${scheduler.quartz.instanceId}")
    private String instanceId;

    @Value("${scheduler.quartz.jobStore.useProperties}")
    private String useProperties;

//    @Value("${scheduler.quartz.jobStore.tablePrefix}")
//    private String tablePrefix;

    @Value("${scheduler.quartz.jobStore.isClustered}")
    private String isClustered;

    @Value("${scheduler.quartz.skipUpdateCheck}")
    private String skipUpdateCheck;

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(
            @Autowired JobFactory jobFactory,
            @Autowired DataSource dataSource,
            @Autowired PlatformTransactionManager transactionManager) {
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
//			factory.setApplicationContext(applicationContext);
        factory.setAutoStartup(true); //pere
        factory.setWaitForJobsToCompleteOnShutdown(true); //pere
        factory.setOverwriteExistingJobs(true); //pere

        factory.setSchedulerName("quartzScheduler"); //Sheduler.quartq

        factory.setDataSource(dataSource);
        factory.setTransactionManager(transactionManager);
        factory.setApplicationContextSchedulerContextKey("applicationContext");

        factory.setJobFactory(jobFactory);

        Properties props = new ManagedProperties();

//        props.setProperty("org.quartz.scheduler.instanceId", "AUTO");
//        props.setProperty("org.quartz.jobStore.useProperties", "false");
        props.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
//        props.setProperty("org.quartz.jobStore.isClustered", "false");
//        props.setProperty("org.quartz.scheduler.skipUpdateCheck", "true");
        props.setProperty("org.quartz.scheduler.instanceId", instanceId);
        props.setProperty("org.quartz.jobStore.useProperties", useProperties);
//        props.setProperty("org.quartz.jobStore.tablePrefix", tablePrefix);
        props.setProperty("org.quartz.jobStore.isClustered", isClustered);
        props.setProperty("org.quartz.scheduler.skipUpdateCheck", skipUpdateCheck);
//        props.setProperty("org.quartz.threadPool.makeThreadsDaemons", "true");
//        props.setProperty("org.quartz.scheduler.makeSchedulerThreadDaemon", "true");
//        props.setProperty("org.quartz.scheduler.interruptJobsOnShutdown", "true");
//        props.setProperty("org.quartz.plugin.shutdownhook.cleanShutdown", "true");
//        props.setProperty("org.quartz.plugin.shutdownhook.class", "org.quartz.plugins.management.ShutdownHookPlugin");
//			props.setProperty("org.quartz.jobStore.driverDelegateClass", org.quartz.impl."");
//			props.setProperty("org.quartz.threadPool.threadCount", "");
//			props.setProperty("org.quartz.jobStore.misfireThreshold", "");
//			props.setProperty("", "");

        factory.setQuartzProperties(props); //zasetit environment - eto zdorovenniy prop file - ego autowired nujno suda
        return factory;
    }

    @Bean
    public JobFactory autowiredQuartzJobFactory() {
        return new AutowiredQuartzJobFactory();
    }
}
