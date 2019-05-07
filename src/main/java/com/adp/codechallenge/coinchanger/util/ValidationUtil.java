package com.adp.codechallenge.coinchanger.util;

import java.util.Arrays;
import java.util.List;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.exception.ValidationException;

public class ValidationUtil {
	
	private static List<Integer> validBillValues = Arrays.asList(1,2,5,10,20,50,100);
	
	public static void runValidations(CoinExchangeRequest request) throws ValidationException{
		
		if(!request.getBill().matches("[0-9]+")) {
			throw new ValidationException("Not a valid Bill denomination");
		}
		Integer billAmt = Integer.valueOf(request.getBill());
		boolean isValid = validBillValues.stream().anyMatch(i -> i==billAmt);
		if(!isValid) {
			throw new ValidationException("Not a valid Bill denomination");
		}
	}

}
