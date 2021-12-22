package com.nagarro.unittesting.eBroker.api;

import org.hamcrest.CoreMatchers;
import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import com.nagarro.unittesting.eBroker.dao.TraderRepository;
import com.nagarro.unittesting.eBroker.utils.EquityUtililty;

@SpringBootTest
@AutoConfigureMockMvc
public class EquityResourceIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private TraderRepository traderRepo;

	@Test
	public void testAddFundBuySellEquity() throws Exception {
		// add fund
		mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/add/fund/1?fundsToBeAdded=100"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is("Success")));

		// buy equity

		String expected = "validation.failed";
		if (EquityUtililty.checkForValidDayAndTime()) {
			expected = "Success";
		}

		mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/buy/123").contentType(MediaType.APPLICATION_JSON)
				.content("{\r\n" + "    \"traderId\":1,\r\n" + "    \"funds\": 100\r\n" + "}"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(expected)));
		String sellExpected = "Success";
		if (!expected.equalsIgnoreCase("Success")) {
			sellExpected = "validation.failed";
		}

		// Sell equity
		mockMvc.perform(MockMvcRequestBuilders.post("/api/equity/sell/123/1"))
				.andExpect(MockMvcResultMatchers.status().isOk()).andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.jsonPath("$", CoreMatchers.is(sellExpected)));
		
		// check funds available at the end (100 in the bootstap and 100 added = 200)
		MatcherAssert.assertThat(this.traderRepo.findByTraderId(1).get().getFundsAvailable(), CoreMatchers.is(200));
	}

}
