package com.jpmc.salesprocessor.service;

import java.util.List;

import com.jpmc.salesprocessor.model.Sale;

public interface AdjustmentService {

	void adjustAllSales(Sale adjustmentSale, List<Sale> sales);
}
