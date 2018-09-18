package com.airdrop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author ShengGuang.Ye
 * @version V1.0
 * @Description: 线程池配置
 * @date 2018/8/16 14:33
 */
@Configuration
@EnableAsync
public class ExecutorConfig {

    private int corePoolSize = 5;//线程池维护线程的最少数量

    private int maxPoolSize = 10;//线程池维护线程的最大数量

    private int queueCapacity = 8; //缓存队列

    private int keepAlive = 60;//允许的空闲时间

    @Bean
    public Executor myExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(corePoolSize);
        executor.setMaxPoolSize(maxPoolSize);
        executor.setQueueCapacity(queueCapacity);
        executor.setThreadNamePrefix("mqExecutor-");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是由调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy()); //对拒绝task的处理策略
        executor.setKeepAliveSeconds(keepAlive);
        executor.initialize();
        return executor;
    }

}
