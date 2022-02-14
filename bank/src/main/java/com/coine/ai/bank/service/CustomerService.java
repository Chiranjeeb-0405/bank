package com.coine.ai.bank.service;

import java.util.List;

import com.coine.ai.bank.Exceptionn.BankException;
import com.coine.ai.bank.dto.CustomerDTO;

public interface CustomerService {
	public Integer addCustomer(CustomerDTO customerDTO) throws BankException;
	public CustomerDTO getCustomer(Integer customerId) throws BankException;
	public void updateCustomer(Integer customerId, String emailId)throws BankException;
	public void deleteCustomer(Integer customerId)throws BankException;
	public List<CustomerDTO> getAllCustomers() throws BankException;
}

