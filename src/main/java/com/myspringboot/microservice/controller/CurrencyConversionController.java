/**
 * 
 */
package com.myspringboot.microservice.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.myspringboot.microservice.bean.CurrencyConversionBean;

/**
 * @author 30069
 *
 */
@RestController
public class CurrencyConversionController {
	private Logger logger = LoggerFactory.getLogger(CurrencyConversionController.class);
	
	@Autowired
	CurrencyExchangeServiceProxy fiegnProxy;
	
	@RequestMapping(path="/", method = RequestMethod.GET)
	public String defaultRoute() {
		logger.info("Entering defaultRoute@CurrencyConversionController");
		return "Hello 'Currency Conversion' Microservices";
	}
	
	@GetMapping("/convert-currency/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean convertCurrency(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		logger.info("Entering convertCurrency@CurrencyConversionController");
		String forexServiceUrl = "http://localhost:8080/forex-service/from/{from}/to/{to}";
		
		Map<String, String> uriVariables = new HashMap<>();
		uriVariables.put("from", from);
		uriVariables.put("to", to);
		
		ResponseEntity<CurrencyConversionBean> responseEntity = new RestTemplate().getForEntity(forexServiceUrl, CurrencyConversionBean.class, uriVariables);
		CurrencyConversionBean response = responseEntity.getBody();
		
		logger.info("Exiting convertCurrency@CurrencyConversionController");
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}

	@GetMapping("/convert-currency-feign/from/{from}/to/{to}/quantity/{quantity}")
	public CurrencyConversionBean getCurrencyExchangeValueFeignProxy(@PathVariable String from, @PathVariable String to, @PathVariable BigDecimal quantity) {
		logger.info("Entering getCurrencyExchangeValueFeignProxy@CurrencyConversionController");
		CurrencyConversionBean response = fiegnProxy.getExchangeValue(from, to);
		logger.info("{}", response);
		logger.info("Exiting getCurrencyExchangeValueFeignProxy@CurrencyConversionController");
		return new CurrencyConversionBean(response.getId(), from, to, response.getConversionMultiple(), quantity, quantity.multiply(response.getConversionMultiple()), response.getPort());
	}
}
