package com.adp.codechallenge.coinchanger.machine;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.exception.NotEnoughCoinsException;
import com.adp.codechallenge.coinchanger.service.CoinName;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@Service
public class CoinChangerMachine {

	@Autowired
	private CoinStore coinStore;

	@Autowired
	private CoinCalculator coinCalculator;

	public CoinDTO calculateNumberOfCoins(int billAmount) throws Exception{

		double totalInventoryValue = coinStore.getTotalInventoryValue();
		if (billAmount > totalInventoryValue) {
			throw new NotEnoughCoinsException("Not enough coins are available to exchange for bill");
		}
		CoinDTO updateInvtoryDto = new CoinDTO();
		synchronized (this) {
//			System.out.println("Enter synchronized block");
			CoinDTO inventoryDto = coinStore.getCoinInventory();
			Map<CoinName, Integer> resultMap = coinCalculator.getNoOfCoins(billAmount, inventoryDto);
//			Thread.currentThread().sleep(2000);
			updateInvtoryDto.setQuarter(resultMap.get(CoinName.Quarter));
			updateInvtoryDto.setDime(resultMap.get(CoinName.Dime));
			updateInvtoryDto.setNickel(resultMap.get(CoinName.Nickel));
			updateInvtoryDto.setPenny(resultMap.get(CoinName.Penny));
			coinStore.updateCoinInventory(updateInvtoryDto);
//			System.out.println("Exiting synchronized block");
		}
		return updateInvtoryDto;

	}

}
