package com.coine.ai.bank.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coine.ai.bank.Exceptionn.BankException;
import com.coine.ai.bank.dto.CustomerDTO;
import com.coine.ai.bank.entity.Customer;
import com.coine.ai.bank.repository.CustomerRepository;


@Service
public class CustomerServiceImpl implements CustomerService {
	@Autowired
	private CustomerRepository customerRepository;
	@Override
	public CustomerDTO getCustomer(Integer customerId) throws BankException {
		Optional<Customer> optional = customerRepository.findById(customerId);
		Customer customer = optional.orElseThrow(() -> new BankException("Service.CUSTOMER_NOT_FOUND"));
		CustomerDTO customer2 = new CustomerDTO();
		customer2.setCustomerId(customer.getCustomerId());
		customer2.setDateOfBirth(customer.getDateOfBirth());
		customer2.setEmailId(customer.getEmailId());
		customer2.setName(customer.getName());
		return customer2;
	}
	@Override
	public Integer addCustomer(CustomerDTO customerDTO) throws BankException {
		Customer customerEntity = new Customer();
		customerEntity.setDateOfBirth(customerDTO.getDateOfBirth());
		customerEntity.setEmailId(customerDTO.getEmailId());
		customerEntity.setName(customerDTO.getName());
		customerEntity.setCustomerId(customerDTO.getCustomerId());
		Customer customerEntity2 = customerRepository.save(customerEntity);
		return customerEntity2.getCustomerId();
	}
	@Override
	public void updateCustomer(Integer customerId, String emailId) throws BankException {
		Optional<Customer> customer = customerRepository.findById(customerId);
		Customer c = customer.orElseThrow(() -> new BankException("Service.CUSTOMER_NOT_FOUND"));
		c.setEmailId(emailId);
		customerRepository.save(c);
	}
	@Override
	public void deleteCustomer(Integer customerId) throws BankException {
		Optional<Customer> customer = customerRepository.findById(customerId);
		customer.orElseThrow(() -> new BankException("Service.CUSTOMER_NOT_FOUND"));
		customerRepository.deleteById(customerId);
	}
	@Override
	public List<CustomerDTO> getAllCustomers() throws BankException {
		
		Iterable<Customer> customers = customerRepository.findAll();
		List<CustomerDTO> customerDTOs = new ArrayList<>();
		customers.forEach(customer -> {
			CustomerDTO cust = new CustomerDTO();
			cust.setCustomerId(customer.getCustomerId());
			cust.setDateOfBirth(customer.getDateOfBirth());
			cust.setEmailId(customer.getEmailId());
			cust.setName(customer.getName());
			customerDTOs.add(cust);
		});
		if (customerDTOs.isEmpty())
			throw new BankException("Service.CUSTOMERS_NOT_FOUND");
		return customerDTOs;
	}
}

