package com.adp.codechallenge.coinchanger.machine;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;

@Service
public interface CoinCalculator {

	public Map<CoinName, Integer> getNoOfCoins(int billAmount, CoinDTO coinDto);
}
