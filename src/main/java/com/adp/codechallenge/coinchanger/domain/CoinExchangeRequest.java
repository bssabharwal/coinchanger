package com.adp.codechallenge.coinchanger.domain;

import javax.validation.Valid;

public class CoinExchangeRequest {

	private String bill;

	public String getBill() {
		return bill;
	}

	public void setBill(String bill) {
		this.bill = bill;
	}
	
	
}
