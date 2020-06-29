package com.springframework.services;

import com.springframework.api.v1.model.CustomerDTO;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CustomerService {
    List<CustomerDTO> getAllCustomers();
    CustomerDTO getCustomerById(Long id);
}
