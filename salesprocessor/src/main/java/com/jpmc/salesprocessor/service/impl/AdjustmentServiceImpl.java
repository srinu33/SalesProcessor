package com.jpmc.salesprocessor.service.impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jpmc.salesprocessor.model.Sale;
import com.jpmc.salesprocessor.service.AdjustmentService;

@Service
public class AdjustmentServiceImpl implements AdjustmentService {

	@Override
	public void adjustAllSales(Sale adjustmentSale, List<Sale> sales) {
		sales.stream()
			 .filter(s -> s.getType().equals(adjustmentSale.getType()))
			 .forEach((s)->applyAdjustment(s, adjustmentSale));
	}
	
	private void applyAdjustment(Sale sale, Sale adjustmentSale) {
		if(sale.isAdjustment()) {
			return;
		}

		BigDecimal price = sale.getPrice();
		switch(adjustmentSale.getAdjustType()) {
			case ADD:
				sale.setPrice(price.add(adjustmentSale.getAdjustValue()));
				break;
				
			case SUB:
				sale.setPrice(price.subtract(adjustmentSale.getAdjustValue()));
				break;
				
			case MUL:
				sale.setPrice(price.multiply(adjustmentSale.getAdjustValue()));
				break;
		}
	}

}
