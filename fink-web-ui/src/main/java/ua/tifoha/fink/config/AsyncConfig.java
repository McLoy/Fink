package ua.tifoha.fink.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
@EnableAsync
@PropertySource ("classpath:application.properties")
public class AsyncConfig extends AsyncConfigurerSupport {
	@Value ("${async.executor.corePoolSize:2}")
	private int corePoolSize;
	@Value ("${async.executor.maxPoolSize:10}")
	private int maxPoolSize;
	@Value ("${async.executor.queueCapacity:500}")
	private int queueCapacity;
	@Value ("${async.executor.threadNamePrefix:}")
	private String threadNamePrefix;

	@Override
	public Executor getAsyncExecutor() {
		return asyncTaskExecutor();
	}

	@Bean
	public AsyncTaskExecutor asyncTaskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(corePoolSize);
		executor.setMaxPoolSize(maxPoolSize);
		executor.setQueueCapacity(queueCapacity);
		executor.setThreadNamePrefix(threadNamePrefix);
		executor.initialize();
		return executor;
	}
}
