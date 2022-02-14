package com.coine.ai.bank.Controller;


import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coine.ai.bank.Exceptionn.BankException;
import com.coine.ai.bank.dto.CustomerDTO;
import com.coine.ai.bank.service.CustomerService;


@RestController
@RequestMapping(value = "/bank")
@Validated
public class CustomerController {
	
//	private static final Logger LOGGER=LoggerFactory.getLogger(CustomerController.class);
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private Environment environment;
	@GetMapping(value = "/customers")
	public ResponseEntity<List<CustomerDTO>> getAllCustomers() throws BankException {
		List<CustomerDTO> customerList = customerService.getAllCustomers();
		return new ResponseEntity<>(customerList, HttpStatus.OK);
	}
	@GetMapping(value = "/customers/{customerId}")
	public ResponseEntity<CustomerDTO> getCustomerDetails(@PathVariable @Min(value = 1, message = "{customer.customerid.invalid}") 
	@Max(value = 100, message = "{customer.customerid.invalid}") Integer customerId)  throws BankException{  
		CustomerDTO customer = customerService.getCustomer(customerId);
//		LOGGER.info("inside getCustomerDetails");
		return new ResponseEntity<>(customer, HttpStatus.OK);
	}
	@PostMapping(value = "/customers")
	public ResponseEntity<String> addCustomer(@Valid @RequestBody CustomerDTO customer) throws BankException {
		Integer customerId = customerService.addCustomer(customer);
		String successMessage = environment.getProperty("API.INSERT_SUCCESS") + customerId;
		return new ResponseEntity<>(successMessage, HttpStatus.CREATED);
	}
	@PutMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> updateCustomer(@PathVariable Integer customerId, @RequestBody CustomerDTO customer)
			throws BankException {
		customerService.updateCustomer(customerId, customer.getEmailId());
		String successMessage = environment.getProperty("API.UPDATE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}
	@DeleteMapping(value = "/customers/{customerId}")
	public ResponseEntity<String> deleteCustomer(@PathVariable Integer customerId) throws BankException {
		customerService.deleteCustomer(customerId);
		String successMessage = environment.getProperty("API.DELETE_SUCCESS");
		return new ResponseEntity<>(successMessage, HttpStatus.OK);
	}
}
