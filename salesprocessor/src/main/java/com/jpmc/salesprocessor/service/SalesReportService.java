package com.jpmc.salesprocessor.service;

import java.util.List;

import com.jpmc.salesprocessor.model.Sale;

public interface SalesReportService {

	void generateSalesReport(List<Sale> salesList);
	
	void generateAdjustmentsReport(List<Sale> salesList);
	
	void generateFinalPriceSAfterAdjustments(List<Sale> salesList);
}
