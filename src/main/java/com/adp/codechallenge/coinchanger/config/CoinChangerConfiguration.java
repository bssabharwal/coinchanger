package com.adp.codechallenge.coinchanger.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.adp.codechallenge.coinchanger.machine.CoinCalculator;
import com.adp.codechallenge.coinchanger.machine.LessCoinCalculatorImpl;
import com.adp.codechallenge.coinchanger.machine.MoreCoinCalculatorImpl;

@Configuration
public class CoinChangerConfiguration {
	
	@Value("${coin.algorithm}")
	private String coinAlgo;
	
	@Bean
	public CoinCalculator getCoinCalculator() {
		
		if("less".equalsIgnoreCase(coinAlgo)) {
			return new LessCoinCalculatorImpl();
		}else {
			return new MoreCoinCalculatorImpl();
		}
	}

}
