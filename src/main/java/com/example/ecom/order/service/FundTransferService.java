package com.example.ecom.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.ecom.order.model.FundTransfer;

@FeignClient(url = "http://localhost:9014/fund/", value = "fundTransferService")
public interface FundTransferService {
	@PutMapping("/")
	public ResponseEntity<Boolean> transfer(@RequestBody FundTransfer fundTransfer);
}
