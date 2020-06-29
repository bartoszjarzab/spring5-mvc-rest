package com.springframework.controllers.v1;

import com.springframework.api.v1.model.CustomerDTO;
import com.springframework.api.v1.model.CustomerListDTO;
import com.springframework.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping({"","/"})
    public ResponseEntity<CustomerListDTO> getAllCustomers(){

        return new ResponseEntity<>(
                new CustomerListDTO(customerService.getAllCustomers()), HttpStatus.OK);
    }
    @GetMapping({"/{id}","/{id}/"})
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){

        return new ResponseEntity<>(
                customerService.getCustomerById(id),HttpStatus.OK);
    }

    @PostMapping({"","/"})
    public ResponseEntity<CustomerDTO> createNewCustomer(@RequestBody CustomerDTO customerDTO){


        return new ResponseEntity<>(customerService.createNewCustomer(customerDTO),HttpStatus.CREATED);
    }

    @PutMapping({"/{id}","/{id}/"})
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO, @PathVariable Long id){


        return new ResponseEntity<>(customerService.saveCustomerByDTO(id,customerDTO),HttpStatus.OK);
    }
}
