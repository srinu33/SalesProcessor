package com.jpmc.salesprocessor;

import static org.junit.Assert.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jpmc.salesprocessor.constants.ItemType;
import com.jpmc.salesprocessor.model.Sale;
import com.jpmc.salesprocessor.model.SalesList;
import com.jpmc.salesprocessor.service.AdjustmentService;

@RunWith(SpringRunner.class)
@SpringBootTest
@DirtiesContext
public class SalesProcessorApplicationTest {

	@Autowired
	AdjustmentService adjustmentService;
	
	protected MockMvc mvc;
	   
	@Autowired
	WebApplicationContext webApplicationContext;

	   
	List<Sale> sales = new ArrayList<Sale>();
	
	SalesList validSalesList;
	
	SalesList inValidSalesList;
	
	private ObjectMapper mapper;
	
	@Before
	public void setUp() {
		sales.add(new Sale(ItemType.APPLE, new BigDecimal(1), 1, false, null, null));
		sales.add(new Sale(ItemType.BANANA, new BigDecimal(3), 1, false, null, null));
		sales.add(new Sale(ItemType.BREAD, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.MILK, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.APPLE, new BigDecimal(1), 1, false, null, null));
		sales.add(new Sale(ItemType.BREAD, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.ONION, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.TOMATO, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.COFFE, new BigDecimal(2), 1, false, null, null));
		sales.add(new Sale(ItemType.BREAD, new BigDecimal(2), 1, false, null, null));
		
		validSalesList = new SalesList();
		validSalesList.setSales(sales);
		
		mapper = new ObjectMapper();
		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		
	}
	
	@Test
	public void testPost_Valid_Sales() throws Exception {
		String uri = "/sales";
		 MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post(uri)
				 .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(validSalesList))
				 .contentType(MediaType.APPLICATION_JSON)).andReturn();
		 
		 assertEquals(200, mvcResult.getResponse().getStatus());
	}
	
}
