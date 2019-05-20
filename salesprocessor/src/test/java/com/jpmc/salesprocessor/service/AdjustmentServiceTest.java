package com.jpmc.salesprocessor.service;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.jpmc.salesprocessor.SalesProcessorApplication;
import com.jpmc.salesprocessor.constants.ItemType;
import com.jpmc.salesprocessor.constants.Operation;
import com.jpmc.salesprocessor.model.Sale;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = SalesProcessorApplication.class)
@WebAppConfiguration
@DirtiesContext
public class AdjustmentServiceTest {

	@Autowired
	AdjustmentService adjustmentService;
	
	Sale adjustment;
	
	final List<Sale> salesList = new ArrayList<Sale>();
	
	@Before
	public void setUp() {
		adjustment =  new Sale(ItemType.APPLE, null, 1, true, new BigDecimal(5), Operation.ADD);
		salesList.add(new Sale(ItemType.APPLE, new BigDecimal(2), 1, false, null, null));
	 }
	
	@Test
	public void testAdjustAllSales() {
		adjustmentService.adjustAllSales(adjustment, salesList);
		
		//Initial Apple Value -  5,  adjustment operation - ADD - 2, so final value -- (5+2) = 7
		assertEquals(new BigDecimal(7), salesList.get(0).getPrice());
	}

}
