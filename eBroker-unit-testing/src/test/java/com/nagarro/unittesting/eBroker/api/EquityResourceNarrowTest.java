package com.nagarro.unittesting.eBroker.api;

import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.unittesting.eBroker.services.impl.EquityServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EquityResource.class)
public class EquityResourceNarrowTest {

	@MockBean
	private EquityServiceImpl employeeService;

	@Autowired
	private MockMvc mockMvc;

	@Test
    public void testBuyEquity() throws Exception {
    	Mockito.when(this.employeeService.buyEquity(Mockito.anyInt(), Mockito.any())).thenReturn("Success");
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/buy/1").contentType(MediaType.APPLICATION_JSON).content("{\r\n"
    			+ "    \"traderId\":1,\r\n"
    			+ "    \"funds\": 1000\r\n"
    			+ "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("Success")));
    }
	
	@Test
    public void testBuyEquity_Exception() throws Exception {
    	Mockito.when(this.employeeService.buyEquity(Mockito.anyInt(), Mockito.any())).thenThrow(new Exception("trader.notfound"));
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/buy/1").contentType(MediaType.APPLICATION_JSON).content("{\r\n"
    			+ "    \"traderId\":1,\r\n"
    			+ "    \"funds\": 1000\r\n"
    			+ "}"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("trader.notfound")));
    }
	
	@Test
    public void testSellEquity() throws Exception {
    	Mockito.when(this.employeeService.sellEquity(Mockito.anyInt(), Mockito.any())).thenReturn("Success");
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/sell/123/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("Success")));
    }
	
	@Test
    public void testSellEquity_Exception() throws Exception {
    	Mockito.when(this.employeeService.sellEquity(Mockito.anyInt(), Mockito.any())).thenThrow(new Exception("trader.notfound"));
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/sell/123/1"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("trader.notfound")));
    }
	
	@Test
    public void testAddFunds() throws Exception {
    	Mockito.when(this.employeeService.addFunds(Mockito.anyInt(), Mockito.any())).thenReturn("Success");
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/add/fund/1?fundsToBeAdded=100"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("Success")));
    }
	
	@Test
    public void testAddFunds_Exception() throws Exception {
    	Mockito.when(this.employeeService.addFunds(Mockito.anyInt(), Mockito.any())).thenThrow(new Exception("trader.notfound"));
    	mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/add/fund/1?fundsToBeAdded=100"))
        .andExpect(MockMvcResultMatchers.status().isOk())
        .andDo(MockMvcResultHandlers.print())
        .andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("trader.notfound")));
    }

}
