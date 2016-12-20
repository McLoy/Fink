package ua.tifoha.fink.services;

import static org.assertj.core.api.Assertions.assertThat;

import javax.sql.DataSource;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.quartz.DateBuilder;
import org.quartz.Job;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.triggers.SimpleTriggerImpl;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.ManagedProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;
import ua.tifoha.fink.quartz.PersistentTrigger;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {JobServiceTest.AppConfig.class})
@ActiveProfiles({"MySql"})
//@Transactional
public class JobServiceTest {
    @Autowired
    private JobService sut;

    @Autowired
    private Scheduler scheduler;

    @Autowired
    private ApplicationContext ctx;
    private JobDetail dummyJob1;
    private JobDetail dummyJob2;
    private Trigger trigger1;
    private Trigger trigger2;

    @Before
    public void setUp() throws Exception {
        dummyJob1 = JobBuilder.newJob(HelloJob.class)
//                              .withIdentity("dummyJob", "User1")
                              .withDescription("Just empty job")
                              .storeDurably()
                              .build();
        dummyJob2 = JobBuilder.newJob(HelloJob.class)
//                              .withIdentity("dummyJob", "User2")
                              .withDescription("Just empty job")
                              .storeDurably()
                              .build();

        trigger1 = TriggerBuilder.newTrigger()
//                                 .forJob(dummyJob1)
//                                 .withIdentity("Simple trigger1", "User2")
                                 .endAt(DateBuilder.dateOf(22, 0, 0))
                                 .withDescription("Test trigger description")
                                 .withSchedule(SimpleScheduleBuilder.repeatSecondlyForTotalCount(1))
                                 .build();

        trigger2 = TriggerBuilder.newTrigger()
                                 .forJob(dummyJob2)
//                                 .withIdentity("Simple trigger2", "User2")
                                 .endAt(DateBuilder.dateOf(22, 0, 0))
                                 .withDescription("Test trigger description")
                                 .withSchedule(SimpleScheduleBuilder.repeatSecondlyForever())
                                 .build();
        trigger1 = new PersistentTrigger((SimpleTriggerImpl) trigger1);

        scheduler.scheduleJob(dummyJob1, trigger1);
        scheduler.scheduleJob(dummyJob2, trigger2);
    }

    @Test
    public void shouldGetAllTriggers() throws Exception {

//        TimeUnit.SECONDS.sleep(1);
//        System.out.println(sut.getAllTriggers().size());
//        System.out.println(sut.getAllTriggers());
//        doSomething(ctx);
//        assertThat(sut.getAllTriggers()).isNotEmpty();

    }

//    public static class Task implements JobDetail {
//
//    }
    //    @InjectMocks
//    private JobExecutorManager sut;
//
//    @Mock
//    private JobDao jobDaoMock;
//
//    @Mock
//    private ScheduledExecutorService schedulerMock;
//    private OldJob activeOldJob1;
//    private OldJob activeOldJob2;
//
//    @Test
//    public void shouldLoadAllTasksWhenInit() throws Exception {
//        activeOldJob1 = new OldJob(1L);
//        activeOldJob1.setName("Simple job");
//        activeOldJob1.setActive(true);
//        activeOldJob1.setStartDate(LocalDateTime.of(2016, 11, 19, 0, 0));
//        activeOldJob1.setEndDate(LocalDateTime.of(2016, 11, 20, 0, 0));
//        activeOldJob1.setInterval(Duration.ofMinutes(5));
//        activeOldJob1.setTask(new PingJob("127.0.0.1", 8080, 100, 5));
//
//        activeOldJob2 = new OldJob(2L);
//        activeOldJob2.setName("Simple job");
//        activeOldJob2.setActive(true);
//        activeOldJob2.setStartDate(LocalDateTime.of(2016, 11, 19, 0, 0));
//        activeOldJob2.setEndDate(LocalDateTime.of(2016, 11, 20, 0, 0));
//        activeOldJob2.setInterval(Duration.ofMinutes(5));
//        activeOldJob2.setTask(new PingJob("127.0.0.1", 8080, 100, 5));
//
//        when(jobDaoMock.findAll())
//                .thenReturn(Arrays.asList(activeOldJob1, activeOldJob2));
//
//        sut.init();
//        verify(jobDaoMock).findAll();
//        verify(schedulerMock, times(2)).scheduleAtFixedRate(any(OldJob.class), anyLong(), anyLong(), any(TimeUnit.class));
//        Assertions.assertThat(sut.activeJobs())
//                .isNotEmpty()
//                .hasSize(2)
//                .containsOnly(activeOldJob1, activeOldJob2);
//    }
    @Configuration
//	@EnableScheduling
    @PropertySource(name = "quartz", value = "classpath:quartz.properties")
    @ComponentScan("ua.tifoha.fink")
    public static class AppConfig {
        //			implements SchedulingConfigurer {
        @Autowired
        ApplicationContext applicationContext;

        @Autowired
        private Environment env;

        @Bean
        public SchedulerFactoryBean schedulerFactoryBean(
                @Autowired JobFactory jobFactory,
                @Autowired DataSource dataSource,
                @Autowired PlatformTransactionManager transactionManager) {
            SchedulerFactoryBean factory = new SchedulerFactoryBean();
//			factory.setApplicationContext(applicationContext);
            factory.setAutoStartup(true);
            factory.setWaitForJobsToCompleteOnShutdown(true);
            factory.setOverwriteExistingJobs(true);

            factory.setSchedulerName("quartzScheduler");

            factory.setDataSource(dataSource);
            factory.setTransactionManager(transactionManager);
            factory.setApplicationContextSchedulerContextKey("applicationContext");

            factory.setJobFactory(jobFactory);

            Properties props = new ManagedProperties();
            props.setProperty("org.quartz.scheduler.instanceId", "AUTO");
            props.setProperty("org.quartz.jobStore.useProperties", "false");
            props.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
            props.setProperty("org.quartz.jobStore.isClustered", "true");
//			props.setProperty("org.quartz.jobStore.driverDelegateClass", org.quartz.impl."");
//			props.setProperty("org.quartz.threadPool.threadCount", "");
//			props.setProperty("org.quartz.jobStore.misfireThreshold", "");
//			props.setProperty("", "");
//			props.setProperty("", "");
//			props.setProperty("", "");
//			props.setProperty("", "");

            factory.setQuartzProperties(props);
//			return factory.getObject();
            return factory;
        }

//    @Bean
//    public SpringBeanJobFactory springBeanJobFactory() {
//        return new AutowiredQuartzJobFactory();
//    }

//		@Bean
//		public DisposableBean beanTest() {
//			return new DisposableBean() {
//				@Override
//				public void destroy() throws Exception {
//					System.out.println("AppConfig.destroy");
//				}
//			};
//		}


//		@Override
//		public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
//			scheduledTaskRegistrar.setScheduler(schedulerFactoryBean().getObject());
//		}

            @Bean
            @Profile("MySql")
    public DataSource dataSourceMySql() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/fink");
        dataSource.setUsername("root");
        dataSource.setPassword("415263");

        return dataSource;
    }
//@Bean
//public DataSource dataSource() {
////     no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
//    EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//    return builder
//            .setType(EmbeddedDatabaseType.DERBY) //.H2 or .DERBY
////            .addScript("db/sql/tables_derby.sql")
////            .addScript("db/sql/insert-data.sql")
//            .build();
//}
        @Bean
        @Profile("H2")
        public DataSource dataSourceH2() {
//     no need shutdown, EmbeddedDatabaseFactoryBean will take care of this
            EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
            return builder
                    .setType(EmbeddedDatabaseType.H2) //.H2 or .DERBY
                    .addScript("db/sql/tables_h2.sql")
//            .addScript("db/sql/insert-data.sql")
                    .build();
        }

        @Bean
        public PlatformTransactionManager transactionManager(@Autowired DataSource dataSource) {
            return new DataSourceTransactionManager(dataSource);
        }
    }

    private static void doSomething(ApplicationContext ctx) throws SchedulerException, InterruptedException {
        Scheduler scheduler = ctx.getBean(Scheduler.class);

        JobDetail job = JobBuilder.newJob(HelloJob.class)
//								  .withIdentity("dummyJobName", "group1")
                .usingJobData("host", "remote.alfa-holding.net")
                .usingJobData("port", 3396)
                .usingJobData("attemptCount", 5)
                .build();

        Trigger trigger = TriggerBuilder
                .newTrigger()
                .startNow()
                .withIdentity("dummyTriggerName", "group1")

//                .modifiedByCalendar("oddSeconds")

                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                .withIntervalInMilliseconds(500)
                                .withMisfireHandlingInstructionNowWithExistingCount()
                                .withRepeatCount(5))
                .build();
        scheduler.scheduleJob(job, trigger);
        TimeUnit.SECONDS.sleep(2);
    }

    public static class HelloJob implements Job {
        @Override
        public void execute(JobExecutionContext context) throws JobExecutionException {
            System.out.println("HelloJob.execute: " + context.getFireTime());
        }
    }

}

