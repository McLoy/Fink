package ua.tifoha.fink.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

//import org.springframework.orm.hibernate5.HibernateTransactionManager;
//import org.springframework.orm.hibernate5.LocalSessionFactoryBuilder;

@Configuration
@EnableAsync
//@PropertySource("classpath:application.properties")
public class AsyncConfig extends AsyncConfigurerSupport {

//    @Value("#{async.executor.corePoolSize}.parse")
//    private Integer corePoolSize;
//
//    @Value("${async.executor.maxPoolSize}")
//    private Integer maxPoolSize;
//
//    @Value("${async.executor.queueCapacity}")
//    private Integer queueCapacity;
//
    @Value("${async.executor.threadNamePrefix}")
    private String threadNamePrefix;

    @Override
    public Executor getAsyncExecutor() {
        return asyncTaskExecutor();
    }

    @Bean
    public AsyncTaskExecutor asyncTaskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(2);
        executor.setMaxPoolSize(10);
        executor.setQueueCapacity(500);
//        executor.setThreadNamePrefix("MainExec-");
//        executor.setCorePoolSize(corePoolSize);
//        executor.setMaxPoolSize(maxPoolSize);
//        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix(threadNamePrefix);
//
        executor.initialize();
        return executor;
    }
}
