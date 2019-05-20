package com.jpmc.salesprocessor.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jpmc.salesprocessor.model.Sale;
import com.jpmc.salesprocessor.model.SalesList;
import com.jpmc.salesprocessor.publisher.SalesPublisher;

/**
 * 
 * @author srinivas
 * Rest controller for sales Api's 
 *
 */
@RestController
public class SalesController {
	
	@Autowired
	private SalesPublisher salesPublisher;
	
	/**
	 * POST method - will receive Sales List and call the publisher with those list
	 * @param salesList
	 * @return
	 */
	@PostMapping("/sales")
	public ResponseEntity<String> postSales(@RequestBody SalesList salesList) {
		List<Sale> sales = salesList.getSales();
		ResponseEntity<String> response = null;
		try {
			salesPublisher.publishSales(sales);
			response = new ResponseEntity<String>("Sales Processed Successfully.", HttpStatus.OK);
		}catch(Exception e) {
			response = new ResponseEntity<String>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return response;
	}

}
