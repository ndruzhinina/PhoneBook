package com.example.phonebookwebservice.controllers;

import java.util.List;

import com.example.phonebookwebservice.business.PhoneBookService;
import com.example.phonebookwebservice.models.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/customers")
public class CustomersController {

	private PhoneBookService phoneBookService;

	@Autowired
	public CustomersController(PhoneBookService phoneBookService) {
		this.phoneBookService = phoneBookService;
	}

	@GetMapping
	public List<Customer> getAll() {
		return phoneBookService.findAll();
	}

	@GetMapping
	@RequestMapping("{name}")
	public ResponseEntity<Customer> get(@PathVariable String name) {
		Customer customer = phoneBookService.findByName(name);
		return customer == null
				? ResponseEntity.status(HttpStatus.NOT_FOUND).body(null)
				: new ResponseEntity<>(customer, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<Customer> addNewCustomer(@RequestBody final Customer customer) {
		try {
			return new ResponseEntity<>(phoneBookService.create(customer), HttpStatus.CREATED);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
	}

	@RequestMapping(value = "{name}", method = RequestMethod.PUT)
	public ResponseEntity<Customer> updateCustomer(@PathVariable String name, @RequestBody String phone) {
		try {
			return new ResponseEntity<>(phoneBookService.addPhoneToCustomer(name, phone), HttpStatus.OK);
		} catch (Exception ex) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}

	@RequestMapping(value = "{name}", method = RequestMethod.DELETE)
	public ResponseEntity deleteCustomer(@PathVariable String name) {
		try {
			phoneBookService.removeCustomer(name);
			return ResponseEntity.status(HttpStatus.ACCEPTED).body(null);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
		}
	}
}
