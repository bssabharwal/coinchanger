package com.adp.codechallenge.coinchanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.domain.CoinExchangeResponse;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.machine.CoinChangerMachine;
import com.adp.codechallenge.coinchanger.util.ValidationUtil;

@Service
public class CoinChangerService {

	@Autowired
	private CoinChangerMachine coinChangerMachine;

	public CoinExchangeResponse changeBillForCoins(CoinExchangeRequest request) throws Exception {
		
		ValidationUtil.runValidations(request);
		CoinDTO coinDto = coinChangerMachine.calculateNumberOfCoins(Integer.valueOf(request.getBill()));
		CoinExchangeResponse response = new CoinExchangeResponse();
		response.setQuarter(coinDto.getQuarter());
		response.setDime(coinDto.getDime());
		response.setNickel(coinDto.getNickel());
		response.setPenny(coinDto.getPenny());
		return response;

	}

}
