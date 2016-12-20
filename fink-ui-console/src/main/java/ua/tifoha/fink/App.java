package ua.tifoha.fink;

public class App {
//
//	public static void main(String[] args) throws SQLException, SchedulerException {
//		AbstractApplicationContext appContext = null;
//		try {
//			appContext = new AnnotationConfigApplicationContext(AppConfig.class);
//			appContext.registerShutdownHook();
////			doSomething(appContext);
//		} catch (final Exception e) {
//			// handle exceptions properly here
//			e.printStackTrace();
//		} finally {
//			if (appContext != null) {
//				((AnnotationConfigApplicationContext) appContext).close();
//			}
//		}
//
//
////		scheduler.shutdown();
////		DataSource dataSource = ctx.getBean(DataSource.class);
////		Connection con = dataSource.getConnection();
////		PreparedStatement sta-tement = con.prepareStatement("SELECT * FROM QRTZ_JOB_DETAILS");
////		ResultSet res = statement.executeQuery();
////		res.next();
////		System.out.println(res.getObject(1));
////		con.close();
//
//	}
//
//	private static void doSomething(ApplicationContext ctx) throws SchedulerException, InterruptedException {
//		Scheduler scheduler = ctx.getBean(Scheduler.class);
//
//		JobDetail job = JobBuilder.newJob(HelloJob.class)
////								  .withIdentity("dummyJobName", "group1")
//								  .usingJobData("host", "remote.alfa-holding.net")
//								  .usingJobData("port", 3396)
//								  .usingJobData("attemptCount", 5)
//								  .build();
//
//		Trigger trigger = TriggerBuilder
//				.newTrigger()
//				.startNow()
//				.withIdentity("dummyTriggerName", "group1")
//
////                .modifiedByCalendar("oddSeconds")
//
//				.withSchedule(
//						SimpleScheduleBuilder.simpleSchedule()
//											 .withIntervalInMilliseconds(500)
//											 .withMisfireHandlingInstructionNowWithExistingCount()
//											 .withRepeatCount(5))
//				.build();
//		scheduler.scheduleJob(job, trigger);
//		TimeUnit.SECONDS.sleep(2);
//	}
//
//	public static class HelloJob implements Job {
//		@Override
//		public void execute(JobExecutionContext context) throws JobExecutionException {
//			System.out.println("HelloJob.execute: " + context.getFireTime());
//		}
//	}
//
//	@Configuration
//	public static class AppConfig2 {
//		@Bean (destroyMethod = "destroy")
//		public DisposableBean beanTest() {
//			System.out.println("AppConfig2.beanTest");
//			return new DisposableBean() {
//				@Override
//				public void destroy() throws Exception {
//					System.out.println("AppConfig.destroy");
//				}
//			};
//		}
////		@Bean
////		public InitializingBean beanTest2() {
////			System.out.println("AppConfig2.beanTest");
////			return new InitializingBean() {
////				@Override
////				public void afterPropertiesSet() throws Exception {
////					System.out.println("AppConfig2.afterPropertiesSet");
////				}
////			};
////		}
//
//	}
//
//	@Configuration
////	@EnableScheduling
//	@PropertySource(name = "quartz", value = "classpath:quartz.properties")
//	public static class AppConfig{
////			implements SchedulingConfigurer {
//		@Autowired
//		ApplicationContext applicationContext;
//
//		@Autowired
//		private Environment env;
//
//		@Bean
//		public SchedulerFactoryBean schedulerFactoryBean() {
//			SchedulerFactoryBean factory = new SchedulerFactoryBean();
////			factory.setApplicationContext(applicationContext);
//			factory.setAutoStartup(true);
//			factory.setWaitForJobsToCompleteOnShutdown(true);
//			factory.setOverwriteExistingJobs(true);
//
//			factory.setSchedulerName("quartzScheduler");
//
//			factory.setDataSource(dataSource());
//			factory.setTransactionManager(transactionManager());
//			factory.setApplicationContextSchedulerContextKey("applicationContext");
//
//			factory.setJobFactory(springBeanJobFactory());
//
//			Properties props = new ManagedProperties();
//			props.setProperty("org.quartz.scheduler.instanceId", "AUTO");
//			props.setProperty("org.quartz.jobStore.useProperties", "false");
//			props.setProperty("org.quartz.jobStore.tablePrefix", "QRTZ_");
//			props.setProperty("org.quartz.jobStore.isClustered", "true");
////			props.setProperty("org.quartz.jobStore.driverDelegateClass", org.quartz.impl."");
////			props.setProperty("org.quartz.threadPool.threadCount", "");
////			props.setProperty("org.quartz.jobStore.misfireThreshold", "");
////			props.setProperty("", "");
////			props.setProperty("", "");
////			props.setProperty("", "");
////			props.setProperty("", "");
//
//			factory.setQuartzProperties(props);
////			return factory.getObject();
//			return factory;
//		}
//
//		@Bean
//		public SpringBeanJobFactory springBeanJobFactory() {
//			return new SpringBeanJobFactory(){
//				private AutowireCapableBeanFactory beanFactory = applicationContext.getAutowireCapableBeanFactory();;
//
//				@Override
//				public Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
//					final Object job = super.createJobInstance(bundle);
//					beanFactory
//							.autowireBean(job);  //the magic is done here
//
//					return job;
//				}
//			};
//		}
//
////		@Bean
////		public DisposableBean beanTest() {
////			return new DisposableBean() {
////				@Override
////				public void destroy() throws Exception {
////					System.out.println("AppConfig.destroy");
////				}
////			};
////		}
//
//
////		@Override
////		public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
////			scheduledTaskRegistrar.setScheduler(schedulerFactoryBean().getObject());
////		}
//
//		@Bean
//		public DataSource dataSource() {
//			DriverManagerDataSource dataSource = new DriverManagerDataSource();
//
//			dataSource.setDriverClassName("com.mysql.jdbc.Driver");
//			dataSource.setUrl("jdbc:mysql://localhost:3306/fink");
//			dataSource.setUsername("root");
//			dataSource.setPassword("415263");
//
//			return dataSource;
//		}
//
//		@Bean
//		public PlatformTransactionManager transactionManager() {
//			return new DataSourceTransactionManager(dataSource());
//		}
//	}
}
