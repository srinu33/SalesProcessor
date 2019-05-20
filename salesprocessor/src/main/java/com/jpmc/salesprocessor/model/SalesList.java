package com.jpmc.salesprocessor.model;

import java.io.Serializable;
import java.util.List;

public class SalesList implements Serializable {

	private static final long serialVersionUID = 2L;
	private List<Sale> sales;
	
	public SalesList() {
		
	}

	public List<Sale> getSales() {
		return sales;
	}

	public void setSales(List<Sale> sales) {
		this.sales = sales;
	}
}
