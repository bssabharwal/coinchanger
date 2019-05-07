package com.adp.codechallenge.coinchanger.store;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Repository;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;

@Repository
public class CoinStore {

	private static Map<CoinName, Integer> coinVault = new ConcurrentHashMap<>();

	static {
		coinVault.put(CoinName.Quarter, new Integer(100));
		coinVault.put(CoinName.Dime, new Integer(100));
		coinVault.put(CoinName.Nickel, new Integer(100));
		coinVault.put(CoinName.Penny, new Integer(100));
	}

	public void initialize(CoinDTO coinDto) {
		coinVault.put(CoinName.Quarter, new Integer(coinDto.getQuarter()));
		coinVault.put(CoinName.Dime, new Integer(coinDto.getDime()));
		coinVault.put(CoinName.Nickel, new Integer(coinDto.getNickel()));
		coinVault.put(CoinName.Penny, new Integer(coinDto.getPenny()));

	}

	public CoinDTO getCoinInventory() {
		CoinDTO coinDto = new CoinDTO();
		coinDto.setQuarter(coinVault.get(CoinName.Quarter));
		coinDto.setDime(coinVault.get(CoinName.Dime));
		coinDto.setNickel(coinVault.get(CoinName.Nickel));
		coinDto.setPenny(coinVault.get(CoinName.Penny));
		return coinDto;

	}

	public double getTotalInventoryValue() {
		double totalValue = 0.00;
		for (Map.Entry<CoinName, Integer> entry : coinVault.entrySet()) {
			if (entry.getKey() == CoinName.Quarter)
				totalValue += entry.getValue() * 0.25;
			else if (entry.getKey() == CoinName.Dime)
				totalValue += entry.getValue() * 0.10;
			else if (entry.getKey() == CoinName.Nickel)
				totalValue += entry.getValue() * 0.05;
			else if (entry.getKey() == CoinName.Penny)
				totalValue += entry.getValue() * 0.01;
		}
		return totalValue;
	}

	public void updateCoinInventory(CoinDTO coinDto) {

		if (coinDto.getQuarter() != 0) {
			int updatedQuarter = coinVault.get(CoinName.Quarter) - coinDto.getQuarter();
			coinVault.put(CoinName.Quarter, new Integer(updatedQuarter));
		}
		if (coinDto.getDime() != 0) {
			int updatedDime = coinVault.get(CoinName.Dime) - coinDto.getDime();
			coinVault.put(CoinName.Dime, new Integer(updatedDime));
		}
		if (coinDto.getNickel() != 0) {
			int updatedNickel = coinVault.get(CoinName.Nickel) - coinDto.getNickel();
			coinVault.put(CoinName.Nickel, new Integer(updatedNickel));
		}
		if (coinDto.getPenny() != 0) {
			int updatedPenny = coinVault.get(CoinName.Penny) - coinDto.getPenny();
			coinVault.put(CoinName.Penny, new Integer(updatedPenny));
		}

	}

	public void addCoinInventory(CoinDTO coinDto) {

		if (coinDto.getQuarter() != 0) {
			int updatedQuarter = coinVault.get(CoinName.Quarter) + coinDto.getQuarter();
			coinVault.put(CoinName.Quarter, new Integer(updatedQuarter));
		}
		if (coinDto.getDime() != 0) {
			int updatedDime = coinVault.get(CoinName.Dime) + coinDto.getDime();
			coinVault.put(CoinName.Dime, new Integer(updatedDime));
		}
		if (coinDto.getNickel() != 0) {
			int updatedNickel = coinVault.get(CoinName.Nickel) + coinDto.getNickel();
			coinVault.put(CoinName.Nickel, new Integer(updatedNickel));
		}
		if (coinDto.getPenny() != 0) {
			int updatedPenny = coinVault.get(CoinName.Penny) + coinDto.getPenny();
			coinVault.put(CoinName.Penny, new Integer(updatedPenny));
		}

	}
}
