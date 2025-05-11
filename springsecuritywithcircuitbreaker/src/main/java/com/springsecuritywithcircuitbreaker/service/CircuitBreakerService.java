package com.springsecuritywithcircuitbreaker.service;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.springsecuritywithcircuitbreaker.dto.ResponseDto;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class CircuitBreakerService {

	private final RestTemplate restTemplate = new RestTemplate();

	@CircuitBreaker(name = "myService", fallbackMethod = "fallbackResponse")
	public ResponseDto callExternalService() {
	    HttpHeaders headers = new HttpHeaders();
	    headers.set("User-Agent", "Mozilla/5.0");

	    HttpEntity<String> entity = new HttpEntity<>(headers);

	    ResponseEntity<ResponseDto> response = restTemplate.exchange(
	        "https://reqres.in/api/users?page=2",
	        HttpMethod.GET,
	        entity,
	        ResponseDto.class
	    );
	    
	    ResponseDto responseBody = response.getBody();
	    if(responseBody != null && responseBody.getPage() > 0) {
	    	responseBody.setStatus("success");
	    } else {
	    	responseBody.setStatus("failure");
	    }
	    return response.getBody();
	}


	public ResponseDto fallbackResponse(Throwable t) {
	    return new ResponseDto(); // or populate with fallback data
	}

}
