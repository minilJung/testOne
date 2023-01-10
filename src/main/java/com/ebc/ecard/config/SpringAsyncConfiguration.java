package com.ebc.ecard.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
@EnableAsync
public class SpringAsyncConfiguration {

    @Bean(name = "applicationThreadPoolExecutor")
    public Executor applicationThreadExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(30); // 최대 스레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("Application-Executor-");
        return taskExecutor;
    }

    @Bean(name = "schedulerThreadPoolExecutor")
    public Executor schedulerThreadPoolExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3); // 기본 스레드 수
        taskExecutor.setMaxPoolSize(30); // 최대 스레드 수
        taskExecutor.setQueueCapacity(100); // Queue 사이즈
        taskExecutor.setThreadNamePrefix("Scheduler-Executor-");
        return taskExecutor;
    }

}
