package com.jpmc.salesprocessor.config;

import javax.jms.Queue;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;

@Configuration
@EnableJms
/**
 * 
 * @author srinivas
 * JMS configuration class
 */
public class JMSConfig {
	
	public static final String SALES_NOTIFICATION_QUEUE = "sales-notification-queue";

	@Bean
	public Queue queue() {
		return new ActiveMQQueue(SALES_NOTIFICATION_QUEUE);
	}
}
