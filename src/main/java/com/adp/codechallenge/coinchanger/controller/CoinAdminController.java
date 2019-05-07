package com.adp.codechallenge.coinchanger.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.adp.codechallenge.coinchanger.domain.CoinAdminRequest;
import com.adp.codechallenge.coinchanger.dto.CoinDTO;
import com.adp.codechallenge.coinchanger.service.CoinAdminService;

@RestController
public class CoinAdminController {
	
	@Autowired
	CoinAdminService coinAdminService;
	
	@PostMapping("/admin/initialize")
	public void initializeMachine(@RequestBody CoinAdminRequest request) {
		coinAdminService.initializeMachine(request);
	
	}
	
	@PostMapping("/admin/addInventory")
	public void addInventory(@RequestBody CoinAdminRequest request) {
		coinAdminService.addInventory(request);
	
	}
	
	@GetMapping("/admin/getInventory")
	public CoinDTO getInventory() {
		return coinAdminService.getInventory();
	
	}

}
