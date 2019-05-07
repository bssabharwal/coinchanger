package com.adp.codechallenge.coinchanger.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.domain.CoinExchangeResponse;
import com.adp.codechallenge.coinchanger.service.CoinChangerService;

@RestController
public class CoinChangerController {
	
	Logger logger = LoggerFactory.getLogger(CoinChangerController.class);
	
	@Autowired
	private CoinChangerService coinChangerService;
	
	@PostMapping("/exchangeBill")
	public CoinExchangeResponse exhangeBillForCoins(@RequestBody CoinExchangeRequest request) throws Exception{
		logger.info("Processing request for coin exchange");
		return coinChangerService.changeBillForCoins(request);
	}
	
	

}
