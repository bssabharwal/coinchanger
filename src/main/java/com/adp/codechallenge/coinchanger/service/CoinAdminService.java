package com.adp.codechallenge.coinchanger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.domain.CoinAdminRequest;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@Service
public class CoinAdminService {

	@Autowired
	private CoinStore coinStore;
	
	public void initializeMachine(CoinAdminRequest request) {
		if(request != null) {

			CoinDTO coinDto = new CoinDTO();
			coinDto.setQuarter(request.getQuarter());
			coinDto.setDime(request.getDime());
			coinDto.setNickel(request.getNickel());
			coinDto.setPenny(request.getPenny());
			coinStore.initialize(coinDto);
			
		}
	}
	
	public void addInventory(CoinAdminRequest request) {
		if(request != null) {

			CoinDTO coinDto = new CoinDTO();
			coinDto.setQuarter(request.getQuarter());
			coinDto.setDime(request.getDime());
			coinDto.setNickel(request.getNickel());
			coinDto.setPenny(request.getPenny());
			coinStore.addCoinInventory(coinDto);
			
		}
	}
	
	public CoinDTO getInventory() {
		return coinStore.getCoinInventory();
	}
	
	
}
