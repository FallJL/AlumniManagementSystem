package com.scu.ams.basic.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 线程池配置，目前采用这种方法实现异步发送邮件，而并非RabbitMQ消息队列
 */
@Configuration
public class ThreadPoolConfig {

    /**
     * 核心线程池大小
     */
    private static final int CORE_POOL_SIZE = 41;

    /**
     * 最大可创建的线程数
     */
    private static final int MAX_POOL_SIZE = 50;

    /**
     * 队列最大长度
     */
    private static final int QUEUE_CAPACITY = 1000;

    /**
     * 线程池维护线程所允许的空闲时间
     */
    private static final int KEEP_ALIVE_SECONDS = 500;

    @Bean("taskExecutor")
    public ExecutorService executorService(){
        AtomicInteger c = new AtomicInteger(1);
        LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<Runnable>(QUEUE_CAPACITY);
        return new ThreadPoolExecutor(
                CORE_POOL_SIZE,
                MAX_POOL_SIZE,
                KEEP_ALIVE_SECONDS,
                TimeUnit.MILLISECONDS,
                queue,
                r -> new Thread(r, "thread-pool-" + c.getAndIncrement()),
                new ThreadPoolExecutor.DiscardPolicy()
        );
    }
}
