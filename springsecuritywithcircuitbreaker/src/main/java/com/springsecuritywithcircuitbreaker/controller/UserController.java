package com.springsecuritywithcircuitbreaker.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springsecuritywithcircuitbreaker.dto.ResponseDto;
import com.springsecuritywithcircuitbreaker.entity.User;
import com.springsecuritywithcircuitbreaker.repository.UserRepository;
import com.springsecuritywithcircuitbreaker.service.CircuitBreakerService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private CircuitBreakerService circuitBreakerService;

	@GetMapping("/mock")
	public ResponseEntity<ResponseDto> getMockUsers() {
		ResponseDto result = null;
		try {
			ResponseDto response = circuitBreakerService.callExternalService();
			result = response;
			
			/*
			ObjectMapper mapper = new ObjectMapper();
			String jsonString = mapper.writeValueAsString(response);
			result = mapper.readValue(jsonString, ResponseDto.class);*/
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}

		return ResponseEntity.ok(result);
	}

	@GetMapping
	public List<User> getAllUsers() {
		return userRepository.findAll();
	}

	@GetMapping("/{id}")
	public ResponseEntity<User> getUserById(@PathVariable Long id) {
		return userRepository.findById(id).map(user -> ResponseEntity.ok().body(user))
				.orElse(ResponseEntity.notFound().build());
	}

	@PostMapping
	public User createUser(@RequestBody User user) {
		return userRepository.save(user);
	}

	@PutMapping("/{id}")
	public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User user) {
		return userRepository.findById(id).map(existingUser -> {
			existingUser.setName(user.getName());
			existingUser.setEmail(user.getEmail());
			userRepository.save(existingUser);
			return ResponseEntity.ok(existingUser);
		}).orElse(ResponseEntity.notFound().build());
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
		return userRepository.findById(id).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().<Void>build();
		}).orElse(ResponseEntity.notFound().build());
	}
}
