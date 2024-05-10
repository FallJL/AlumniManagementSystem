package com.scu.ams.basic.config;

/**
 * 消息队列RabbitMQ配置
 */
public class MQConfig {
    // 路由交换机名称
    public static final String EMAIL_ROUTE_EXCHANGE = "email-route-exchange";

    // 直连模式
    public static final String DIRECT = "direct";

    // 发送通知 邮件
    public static final String EMAIL_NOTIFICATION = "email-notification";
}

