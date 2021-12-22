package com.nagarro.unittesting.eBroker.dtos.requests;

public class BuyEquityRequstDO {

	private Integer traderId;

	private Integer funds;
	

	public BuyEquityRequstDO() {
		super();
	}

	public BuyEquityRequstDO(Integer traderId, Integer funds) {
		super();
		this.traderId = traderId;
		this.funds = funds;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getFunds() {
		return funds;
	}

	public void setFunds(Integer funds) {
		this.funds = funds;
	}

	

}
