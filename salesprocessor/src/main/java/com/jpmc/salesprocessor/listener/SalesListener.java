package com.jpmc.salesprocessor.listener;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import com.jpmc.salesprocessor.config.JMSConfig;
import com.jpmc.salesprocessor.model.Sale;
import com.jpmc.salesprocessor.service.AdjustmentService;
import com.jpmc.salesprocessor.service.SalesReportService;

/**
 * 
 * @author Srinivasa
 * Listener class to consume sales messages and process them.
 *
 */
@Component
public class SalesListener {

	private static Logger log = LoggerFactory.getLogger(SalesListener.class);
	
	private List<Sale> sales = new ArrayList<Sale>();
	
	@Autowired
	private JmsTemplate jmsTemplate;
	
	@Autowired
	private SalesReportService salesReportService;
	
	@Autowired
	private AdjustmentService adjustmentService;
	
	@Value("${report.sales.count}")
	private Integer SALES_REPORT_COUNT;
	
	@Value("${report.adjustment.count}")
	private Integer ADJUSTMENT_REPORT_COUNT;
	
	@JmsListener(destination = JMSConfig.SALES_NOTIFICATION_QUEUE)
	public void consume(Sale sale) {
		sales.add(sale);
		
		//Handle Adjustment messages
		if(sale.isAdjustment()) {
			adjustmentService.adjustAllSales(sale, sales);
		} 
		
		//Every 10 messages, generating the report.
		if(sales.size() % SALES_REPORT_COUNT == 0) {
			salesReportService.generateSalesReport(sales);
		}
		//Every 50 messages list all adjustments and final prices
		if(sales.size() % ADJUSTMENT_REPORT_COUNT == 0) {
			log.info("Sales Listener is Paused.");
			generateAdjustmentReports(sales);
		}
		
	}
	
	/**
	 * Method will call sales report service to generate Adjustment report and final prices after adjustments
	 * @param salesList
	 */
	private void generateAdjustmentReports(List<Sale> salesList) {
		salesReportService.generateAdjustmentsReport(salesList);
		salesReportService.generateFinalPriceSAfterAdjustments(salesList);
		
	}
}
