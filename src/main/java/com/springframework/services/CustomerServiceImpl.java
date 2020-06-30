package com.springframework.services;

import com.springframework.api.v1.mapper.CustomerMapper;
import com.springframework.api.v1.model.CustomerDTO;
import com.springframework.domain.Customer;
import com.springframework.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    CustomerRepository customerRepository;
    CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerMapper customerMapper,CustomerRepository customerRepository) {
        this.customerMapper = customerMapper;
        this.customerRepository = customerRepository;
    }

    @Override
    public List<CustomerDTO> getAllCustomers() {

        return customerRepository
                .findAll()
                .stream()
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    enhanceWithURL(customer.getId(), customerDTO);
                    return customerDTO;
                })
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDTO getCustomerById(Long id) {

        return customerRepository
                .findById(id)
                .map(customer -> {
                    CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
                    enhanceWithURL(id, customerDTO);
                    return customerDTO;
                })
                .orElseThrow(RuntimeException::new); //todo implement better exception handling


    }
    private CustomerDTO saveAndReturnDAO(Customer customer){
        Customer savedCustomer = customerRepository.save(customer);
        CustomerDTO returnDTO = customerMapper.customerToCustomerDTO(savedCustomer);
        enhanceWithURL(savedCustomer.getId(),returnDTO);
        return returnDTO;
    }
    private CustomerDTO enhanceWithURL(Long id,CustomerDTO customerDTO){
        customerDTO.setCustomerURL("/api/v1/customers/" + id);
        return customerDTO;
    }

    @Override
    public CustomerDTO createNewCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);

        return saveAndReturnDAO(customer);
    }

    @Override
    public CustomerDTO saveCustomerByDTO(Long id, CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setId(id);

        return saveAndReturnDAO(customer);
    }

    @Override
    public CustomerDTO patchCustomer(Long id, CustomerDTO customerDTO) {
        return customerRepository.findById(id).map(customer -> {
                if(customerDTO.getFirstName()!=null){
                    customer.setFirstName(customerDTO.getFirstName());
                }
                if(customerDTO.getLastName()!=null){
                    customer.setLastName(customerDTO.getLastName());
                }
                return saveAndReturnDAO(customer);

        }).orElseThrow(RuntimeException::new); //todo implement better
    }

    @Override
    public void deleteCustomerById(Long id) {
        customerRepository.deleteById(id);
    }


}
