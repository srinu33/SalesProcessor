package com.jpmc.salesprocessor.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.jpmc.salesprocessor.constants.ItemType;
import com.jpmc.salesprocessor.model.Sale;
import com.jpmc.salesprocessor.service.SalesReportService;

/**
 * 
 * @author Srinivasa
 * Service class to generate Sales Report after every 10 messages and generate Adjustment report after 50 messages.
 */
@Service
public class SalesReportServiceImpl implements SalesReportService {

	private static Logger log = LoggerFactory.getLogger(SalesReportServiceImpl.class);
	
	/**
	 * Method to generate detailed  sales report for saved messages.
	 */
	@Override
	public void generateSalesReport(List<Sale> salesList) {
		log.info("Sales Report: (After every 10 msgs) - Total Msgs -> " + ((null != salesList) ? salesList.size(): 0));
		
		Map<ItemType, Integer> salesQuantityMap = salesList.stream()
											 	.filter(sale -> !sale.isAdjustment())
											 	.collect(Collectors.groupingBy(Sale::getType, Collectors.summingInt(Sale::getQuantity)));
		
		Map<ItemType, Double> salesTotalValMap = salesList.stream()
									 .filter(sale -> !sale.isAdjustment())
									 .collect(Collectors.groupingBy(Sale::getType, Collectors.summingDouble(Sale::getTotalValue)));
		
		log.info(" Item  " + "   Quantity    " + "   Total Amount  ");
		if(salesQuantityMap.size() > 0) {
			salesQuantityMap.forEach((k, v) -> {
								log.info(k +"	   "+ v +" 		"+ salesTotalValMap.get(k));
							});
		}
	}

	/**
	 * Method to generate Adjustment report
	 */
	@Override
	public void generateAdjustmentsReport(List<Sale> salesList) {
		log.info("Adjustments Report:  Item  -  Adjustment - Adjust Value");
		
		if(salesList.stream().filter(sale -> sale.isAdjustment()).count() == 0) {
			log.info("No Adjustments received.");
		}else {
			salesList.stream()
					 .filter(sale -> sale.isAdjustment())
					 .forEach(sale ->{
				log.info(sale.getType()+"  "+sale.getAdjustType()+"  "+sale.getAdjustValue());
			});
		}
		
	}
	
	/**
	 * Method to display final price report after adjustments
	 */
	@Override
	public void generateFinalPriceSAfterAdjustments(List<Sale> salesList) {
		log.info("Final Prices Report after applying Adjustments: ");
		
		Map<ItemType, BigDecimal> distinctProductSales = salesList.stream()
				 						.filter(s ->  !s.isAdjustment())
				 						.collect(Collectors.toMap(Sale::getType, Sale::getPrice, (oldVal, newVal) -> oldVal));
		
		distinctProductSales.entrySet().forEach(e -> {
			log.info(e.getKey()+" "+e.getValue());
		});
		
	}
}
