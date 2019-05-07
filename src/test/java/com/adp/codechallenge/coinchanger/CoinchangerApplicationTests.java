package com.adp.codechallenge.coinchanger;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.adp.codechallenge.coinchanger.domain.CoinExchangeRequest;
import com.adp.codechallenge.coinchanger.domain.CoinExchangeResponse;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.exception.NotEnoughCoinsException;
import com.adp.codechallenge.coinchanger.exception.ValidationException;
import com.adp.codechallenge.coinchanger.service.CoinChangerService;
import com.adp.codechallenge.coinchanger.store.CoinStore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CoinchangerApplicationTests {

	@Autowired
	CoinChangerService coinChangerService;

	@Autowired
	CoinStore coinStore;

	@Value("${coin.algorithm}")
	private String coinAlgo;

	@Test
	public void contextLoads() {
	}

	public void initializeCoinStore(CoinDTO coinDto) {

		coinStore.initialize(coinDto);
	}

	@Test
	public void successSingleRequest() throws Exception {
		CoinDTO coinDto = new CoinDTO();
		coinDto.setQuarter(10);
		coinDto.setDime(20);
		coinDto.setNickel(50);
		coinDto.setPenny(100);
		initializeCoinStore(coinDto);
		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill("5");
		CoinExchangeResponse response = coinChangerService.changeBillForCoins(request);
		if ("less".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(10), response.getQuarter());
			assertEquals(Integer.valueOf(20), response.getDime());
			assertEquals(Integer.valueOf(10), response.getNickel());
			assertEquals(Integer.valueOf(0), response.getPenny());
			CoinDTO invDto = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getQuarter()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getDime()));
			assertEquals(Integer.valueOf(40), Integer.valueOf(invDto.getNickel()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto.getPenny()));
		} else if ("more".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(0), response.getQuarter());
			assertEquals(Integer.valueOf(15), response.getDime());
			assertEquals(Integer.valueOf(50), response.getNickel());
			assertEquals(Integer.valueOf(100), response.getPenny());
			CoinDTO invDto = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(10), Integer.valueOf(invDto.getQuarter()));
			assertEquals(Integer.valueOf(5), Integer.valueOf(invDto.getDime()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getNickel()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getPenny()));
		}

	}

	@Test
	public void successMultipleRequest() throws Exception {
		CoinDTO coinDto = new CoinDTO();
		coinDto.setQuarter(20);
		coinDto.setDime(30);
		coinDto.setNickel(100);
		coinDto.setPenny(100);
		initializeCoinStore(coinDto);
		// submit first request
		CoinExchangeRequest request1 = new CoinExchangeRequest();
		request1.setBill("5");
		CoinExchangeResponse response1 = coinChangerService.changeBillForCoins(request1);
		if ("less".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(20), response1.getQuarter());
			assertEquals(Integer.valueOf(0), response1.getDime());
			assertEquals(Integer.valueOf(0), response1.getNickel());
			assertEquals(Integer.valueOf(0), response1.getPenny());
			CoinDTO invDto1 = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto1.getQuarter()));
			assertEquals(Integer.valueOf(30), Integer.valueOf(invDto1.getDime()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto1.getNickel()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto1.getPenny()));
		} else if ("more".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(0), response1.getQuarter());
			assertEquals(Integer.valueOf(0), response1.getDime());
			assertEquals(Integer.valueOf(80), response1.getNickel());
			assertEquals(Integer.valueOf(100), response1.getPenny());
			CoinDTO invDto1 = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto1.getQuarter()));
			assertEquals(Integer.valueOf(30), Integer.valueOf(invDto1.getDime()));
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto1.getNickel()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto1.getPenny()));
		}
		// submit second request
		CoinExchangeRequest request2 = new CoinExchangeRequest();
		request2.setBill("2");
		CoinExchangeResponse response2 = coinChangerService.changeBillForCoins(request2);
		if ("less".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(0), response2.getQuarter());
			assertEquals(Integer.valueOf(20), response2.getDime());
			assertEquals(Integer.valueOf(0), response2.getNickel());
			assertEquals(Integer.valueOf(0), response2.getPenny());
			CoinDTO invDto2 = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto2.getQuarter()));
			assertEquals(Integer.valueOf(10), Integer.valueOf(invDto2.getDime()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto2.getNickel()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto2.getPenny()));
		} else if ("more".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(0), response2.getQuarter());
			assertEquals(Integer.valueOf(10), response2.getDime());
			assertEquals(Integer.valueOf(20), response2.getNickel());
			assertEquals(Integer.valueOf(0), response2.getPenny());
			CoinDTO invDto2 = coinStore.getCoinInventory();
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto2.getQuarter()));
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto2.getDime()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto2.getNickel()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto2.getPenny()));
		}

	}

	@Test
	public void successMultipleConcurrentRequest() throws Exception {
		CoinDTO coinDto = new CoinDTO();
		coinDto.setQuarter(20);
		coinDto.setDime(30);
		coinDto.setNickel(100);
		coinDto.setPenny(100);
		initializeCoinStore(coinDto);

		ExecutorService threadPool = Executors.newFixedThreadPool(2);
		CompletableFuture<CoinExchangeResponse> thread1 = CompletableFuture.supplyAsync(() -> {
			CoinExchangeRequest request = new CoinExchangeRequest();
			request.setBill("5");
			CoinExchangeResponse response = null;
			try {
				response = coinChangerService.changeBillForCoins(request);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return response;
		}, threadPool);

		CompletableFuture<CoinExchangeResponse> thread2 = CompletableFuture.supplyAsync(() -> {
			CoinExchangeRequest request = new CoinExchangeRequest();
			request.setBill("2");
			CoinExchangeResponse response = null;
			try {
				response = coinChangerService.changeBillForCoins(request);
			} catch (Exception e) {

				e.printStackTrace();
			}
			return response;
		}, threadPool);

		CompletableFuture.allOf(thread1,thread2).join();
		threadPool.shutdown();
		CoinDTO invDto = coinStore.getCoinInventory();
		if ("less".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getQuarter()));
			assertEquals(Integer.valueOf(10), Integer.valueOf(invDto.getDime()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto.getNickel()));
			assertEquals(Integer.valueOf(100), Integer.valueOf(invDto.getPenny()));
		}else if ("more".equalsIgnoreCase(coinAlgo)) {
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto.getQuarter()));
			assertEquals(Integer.valueOf(20), Integer.valueOf(invDto.getDime()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getNickel()));
			assertEquals(Integer.valueOf(0), Integer.valueOf(invDto.getPenny()));
		}

	}

	@Test(expected = NotEnoughCoinsException.class)
	public void errorNotEnoughCoins() throws Exception {

		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill("50");
		coinChangerService.changeBillForCoins(request);
	}

	@Test(expected = ValidationException.class)
	public void errorValidation() throws Exception {

		CoinExchangeRequest request = new CoinExchangeRequest();
		request.setBill("3");
		coinChangerService.changeBillForCoins(request);
	}

}
