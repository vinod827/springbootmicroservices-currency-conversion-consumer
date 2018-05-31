package com.myspringboot.microservice.currency.conversion.springbootmicroservicecurrencyconversion;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan("com.myspringboot.microservice.controller")
@SpringBootApplication
@EnableFeignClients("com.myspringboot.microservice.controller")
public class SpringbootMicroserviceCurrencyconversionApplication {

	public static void main(String[] args) {
		ApplicationContext applicationContext = SpringApplication.run(SpringbootMicroserviceCurrencyconversionApplication.class, args);
		System.out.println("******** Listing all sorted beans ***********");
		String[] beans = applicationContext.getBeanDefinitionNames();
		Arrays.sort(beans);
		for(String bean:beans) {
			System.out.println("Sorted bean: "+bean);
		}
		System.out.println("******* All beans listed *********");
	}
}
