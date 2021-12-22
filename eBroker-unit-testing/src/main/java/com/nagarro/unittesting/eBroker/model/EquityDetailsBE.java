package com.nagarro.unittesting.eBroker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class EquityDetailsBE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TRADER_ID")
	private Integer traderId;

	@Column(name = "EQUITY_ID")
	private Integer equityId;

	@Column(name = "FUND")
	private Integer fund;
	
	

	public EquityDetailsBE() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	

	public EquityDetailsBE(Integer traderId, Integer equityId, Integer fund) {
		super();
		this.traderId = traderId;
		this.equityId = equityId;
		this.fund = fund;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTraderId() {
		return traderId;
	}

	public void setTraderId(Integer traderId) {
		this.traderId = traderId;
	}

	public Integer getEquityId() {
		return equityId;
	}

	public void setEquityId(Integer equityId) {
		this.equityId = equityId;
	}

	public Integer getFund() {
		return fund;
	}

	public void setFund(Integer fund) {
		this.fund = fund;
	}
	
	

}
