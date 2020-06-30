package com.springframework.services;

import com.springframework.api.v1.mapper.CustomerMapper;
import com.springframework.api.v1.model.CustomerDTO;
import com.springframework.domain.Customer;
import com.springframework.repositories.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.springframework.controllers.v1.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class CustomerServiceImplTest {

    private static final Long ID=1L;
    private static final String FIRST_NAME ="Name";
    private static final String LAST_NAME ="LastName";

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @BeforeEach
    void setUp() {

        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(CustomerMapper.INSTANCE,customerRepository);
    }

    @Test
    void getAllCustomers() {
        //given
        List<Customer> customers = Arrays.asList(new Customer(),new Customer());

        when(customerRepository.findAll()).thenReturn(customers);

        //when
        List<CustomerDTO> customersDTO = customerService.getAllCustomers();

        //then
        assertEquals(2,customersDTO.size());

    }

    @Test
    void getCustomerById() {
        //given
        Customer customer = new Customer();
        customer.setId(ID);
        customer.setFirstName(FIRST_NAME);
        customer.setLastName(LAST_NAME);

        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        //when
        CustomerDTO customerDTO = customerService.getCustomerById(ID);

        //then
        assertEquals(FIRST_NAME,customerDTO.getFirstName());
        assertEquals(LAST_NAME,customerDTO.getLastName());

    }

    @Test
    void createNewCustomer(){
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setLastName(LAST_NAME);
        savedCustomer.setFirstName(FIRST_NAME);
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(),savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1",savedDTO.getCustomerURL());

    }
    @Test
    void saveCustomerByDTO() throws Exception {

        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);
        customerDTO.setLastName(LAST_NAME);

        Customer savedCustomer = new Customer();
        savedCustomer.setLastName(LAST_NAME);
        savedCustomer.setFirstName(FIRST_NAME);
        savedCustomer.setId(ID);

        when(customerRepository.save(any(Customer.class))).thenReturn(savedCustomer);

        //when
        CustomerDTO savedDTO = customerService.saveCustomerByDTO(ID,customerDTO);

        //then
        assertEquals(customerDTO.getFirstName(),savedDTO.getFirstName());
        assertEquals("/api/v1/customers/1",savedDTO.getCustomerURL());
    }

    @Test
    void deleteCustomerById() {
        customerRepository.deleteById(ID);
        verify(customerRepository,times(1)).deleteById(anyLong());
    }
}