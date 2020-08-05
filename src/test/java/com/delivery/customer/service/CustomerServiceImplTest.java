package com.delivery.customer.service;

import com.delivery.customer.entity.Address;
import com.delivery.customer.entity.AddressType;
import com.delivery.customer.entity.Customer;
import com.delivery.customer.exception.CustomerServiceException;
import com.delivery.customer.repository.CustomerRepository;
import com.delivery.customer.request.AddressCreateRequest;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.response.CustomerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.context.MessageSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerServiceImplTest {

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private AddressService addressService;

    @Mock
    private MessageSource messageSource;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Captor
    private ArgumentCaptor<Customer> customerArgumentCaptor;


    @Test
    public void shouldGetCustomerById() {

        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.emptyList())
                .build();

        when(customerRepository.findById(1L)).thenReturn(Optional.of(customer));

        CustomerDto customerDto = customerService.getCustomerById(1L);

        verify(customerRepository).findById(1L);

        assertEquals("First name", customerDto.getFirstName());
        assertEquals("Last name", customerDto.getLastName());
        assertEquals("123", customerDto.getPhoneNumber());
    }

    @Test
    public void shouldGetAllCustomers() {

        Customer customer = Customer.builder()
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.emptyList())
                .build();

        when(customerRepository.findAll()).thenReturn(Arrays.asList(customer));

        List<CustomerDto> allCustomers = customerService.getAllCustomers();

        verify(customerRepository).findAll();

        CustomerDto customerDto = allCustomers.get(0);

        assertEquals("First name", customerDto.getFirstName());
        assertEquals("Last name", customerDto.getLastName());
        assertEquals("123", customerDto.getPhoneNumber());
    }


    @Test(expected = CustomerServiceException.class)
    public void shouldGetCustomerNotFoundException() {

        when(customerRepository.findById(100L)).thenReturn(Optional.empty());

        customerService.getCustomerById(100L);

        verify(customerRepository).findById(100L);
    }

    @Test
    public void shouldCreateCustomer() {

        AddressCreateRequest addressCreateRequest = AddressCreateRequest.builder()
                .addressLine("New address")
                .addressType("HOME")
                .country("Turkey")
                .city("Ankara")
                .build();

        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.singletonList(addressCreateRequest)).build();

        Address address = Address.builder()
                .customerId(1L)
                .addressLine("New address")
                .addressType(AddressType.HOME)
                .country("Turkey")
                .city("Ankara")
                .build();

        Customer savedCustomer = Customer.builder()
                .id(1L)
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.singletonList(address))
                .build();


        when(customerRepository.save(Mockito.any())).thenReturn(savedCustomer);

        customerService.createCustomer(customerCreateRequest);

        verify(customerRepository).save(customerArgumentCaptor.capture());
        verify(addressService).saveAll(Collections.singletonList(address));


        Customer customerArgumentCaptorValue = customerArgumentCaptor.getValue();

        assertEquals("First name", customerArgumentCaptorValue.getFirstName());
        assertEquals("Last name", customerArgumentCaptorValue.getLastName());
        assertEquals("123", customerArgumentCaptorValue.getPhoneNumber());
    }

}