package com.jpmc.salesprocessor.publisher;

import java.util.List;

import javax.jms.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.jpmc.salesprocessor.model.Sale;

/**
 * 
 * @author Srinivasa
 * JMS Publisher - Publish the sales message to Active MQ
 *
 */
@Component
public class SalesPublisher {

	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private Queue queue;
	
	public String publishSales(List<Sale> sales) {
		sales.forEach(sale -> {
			jmsTemplate.convertAndSend(queue, sale);
		});
		return "Sales published successfully.";
	}
}
