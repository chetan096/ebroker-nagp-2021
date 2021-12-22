package com.nagarro.unittesting.eBroker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TraderDetailsBE {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "TRADER_ID")
	private Integer traderId;

	@Column(name = "FUNDS_AVAILABLE")
	private Integer fundsAvailable;
	
	

	public TraderDetailsBE() {
		super();
	}

	public TraderDetailsBE(Integer traderId, Integer fundsAvailable) {
		super();
		this.traderId = traderId;
		this.fundsAvailable = fundsAvailable;
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

	public Integer getFundsAvailable() {
		return fundsAvailable;
	}

	public void setFundsAvailable(Integer fundsAvailable) {
		this.fundsAvailable = fundsAvailable;
	}

}
