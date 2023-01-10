package com.ebc.ecard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class HealthCheckController {
	
	@GetMapping("/healthy")
	public String healthy(HttpServletRequest request) {
		return "{\"status\":\"UP\"}";
	}
}
