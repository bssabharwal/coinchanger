package com.adp.codechallenge.coinchanger.machine;

import java.util.HashMap;
import java.util.Map;

import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinName;


public class MoreCoinCalculatorImpl implements CoinCalculator{
	
	@Override
	public Map<CoinName, Integer> getNoOfCoins(int billAmount, CoinDTO coinDto) {
		double[] coinDenominations = { 0.25, 0.10, 0.05, 0.01};
		int[] inventoryArray = { coinDto.getQuarter(), coinDto.getDime(),coinDto.getNickel(), coinDto.getPenny()  };
		int len = coinDenominations.length;
		int[] result = new int[len];
		double amount = billAmount;
		for(int i=len-1;i>-1;i--){
            Double d  = amount/coinDenominations[i];
            int no = d.intValue();
            if(no > inventoryArray[i]){
                result[i] = inventoryArray[i];
                amount -= coinDenominations[i] * inventoryArray[i];
            }else{
                result[i] = no;
                break;
            }
        }
		 Map<CoinName, Integer> resultMap = new HashMap<>();
		 resultMap.put(CoinName.Quarter, new Integer(result[0]));
		 resultMap.put(CoinName.Dime, new Integer(result[1]));
		 resultMap.put(CoinName.Nickel, new Integer(result[2]));
		 resultMap.put(CoinName.Penny, new Integer(result[3]));
		 
		return resultMap;
	}

}
