package com.nagarro.unittesting.eBroker.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.unittesting.eBroker.dtos.requests.BuyEquityRequstDO;
import com.nagarro.unittesting.eBroker.services.IEquityService;

@RestController
@RequestMapping("/api/equity")
public class EquityResource {

	@Autowired
	private IEquityService equityService;

	@PostMapping("/buy/{equityId}")
	public String buyEquity(@PathVariable Integer equityId, @RequestBody BuyEquityRequstDO request) {
		try {
			return equityService.buyEquity(equityId, request);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PostMapping("/sell/{equityId}/{traderId}")
	public String sellEquity(@PathVariable Integer equityId, @PathVariable Integer traderId) {
		try {
			return equityService.sellEquity(equityId, traderId);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@PostMapping("/add/fund/{traderId}")
	public String addFunds(@PathVariable Integer traderId, @RequestParam Integer fundsToBeAdded) {
		try {
			return equityService.addFunds(traderId, fundsToBeAdded);
		} catch (Exception e) {
			return e.getMessage();
		}
	}

}
