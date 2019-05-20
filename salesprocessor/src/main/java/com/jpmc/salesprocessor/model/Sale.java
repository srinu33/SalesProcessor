package com.jpmc.salesprocessor.model;

import java.io.Serializable;
import java.math.BigDecimal;

import com.jpmc.salesprocessor.constants.ItemType;
import com.jpmc.salesprocessor.constants.Operation;

public class Sale implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private ItemType type;
	private BigDecimal price;
	private int quantity=1;
	
	private boolean adjustment;
	private BigDecimal adjustValue;
	private Operation adjustType;
	
	public Sale() {
		
	}
	
	public Sale(ItemType type, BigDecimal price, int quantity,
			boolean adjustment, BigDecimal adjustValue, Operation adjustType) {
		super();
		this.type = type;
		this.price = price;
		this.quantity = quantity;
		this.adjustment = adjustment;
		this.adjustValue = adjustValue;
		this.adjustType = adjustType;
	}
	public ItemType getType() {
		return type;
	}
	public void setType(ItemType type) {
		this.type = type;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public boolean isAdjustment() {
		return adjustment;
	}
	public void setAdjustment(boolean adjustment) {
		this.adjustment = adjustment;
	}
	public BigDecimal getAdjustValue() {
		return adjustValue;
	}
	public void setAdjustValue(BigDecimal adjustValue) {
		this.adjustValue = adjustValue;
	}
	public Operation getAdjustType() {
		return adjustType;
	}
	public void setAdjustType(Operation adjustType) {
		this.adjustType = adjustType;
	}
	
	
	/**
	 * Method to calculate sale value =  quantity * price and return it. 
	 * @return
	 */
	public Double getTotalValue() {
		if(this.isAdjustment() || this.getPrice() == null) {
			return null;
		}
		return this.price.multiply(BigDecimal.valueOf(this.quantity)).doubleValue();
	}

}
