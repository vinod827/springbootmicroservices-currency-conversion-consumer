/**
 * 
 */
package com.myspringboot.microservice.controller;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.myspringboot.microservice.bean.CurrencyConversionBean;

/**
 * @author 30069
 *
 */
//@FeignClient(name="forex-service", url="localhost:8080")
@FeignClient(name="forex-service")
@RibbonClient(name="forex-service")
public interface CurrencyExchangeServiceProxy {

	@GetMapping("/forex-service/from/{from}/to/{to}") // URI of the service to be consumed
	public CurrencyConversionBean getExchangeValue(@PathVariable("from") String from, @PathVariable("to") String to);
	
}
