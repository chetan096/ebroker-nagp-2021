package com.nagarro.unittesting.eBroker.services;

import com.nagarro.unittesting.eBroker.dtos.requests.BuyEquityRequstDO;

public interface IEquityService {

	String buyEquity(Integer equityId, BuyEquityRequstDO request) throws Exception;

	String sellEquity(Integer equityId,Integer traderId) throws Exception ;

	String addFunds(Integer  traderId, Integer fundsToBeAdded) throws Exception;

}
