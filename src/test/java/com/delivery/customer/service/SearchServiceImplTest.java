package com.delivery.customer.service;

import com.delivery.customer.response.CustomerDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SearchServiceImplTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private SearchServiceImpl searchService;

    @Test
    public void shouldGetCustomersByCityName() {

        CustomerDto customer = CustomerDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.emptyList())
                .build();

        when(customerService.getByCityName("London")).thenReturn(Collections.singletonList(customer));

        List<CustomerDto> customerDtos = searchService.findCustomersByCity("London");

        verify(customerService).getByCityName("London");

        assertEquals("First name", customerDtos.get(0).getFirstName());
        assertEquals("Last name", customerDtos.get(0).getLastName());
        assertEquals("123", customerDtos.get(0).getPhoneNumber());
    }

    @Test
    public void shouldGetCustomersByPhonePrefix() {

        CustomerDto customer = CustomerDto.builder()
                .firstName("First name")
                .lastName("Last name")
                .phoneNumber("123")
                .addresses(Collections.emptyList())
                .build();

        when(customerService.getByPhoneNumberPrefix("44")).thenReturn(Collections.singletonList(customer));

        List<CustomerDto> customerDtos = searchService.findCustomersByPhoneNumberPrefix("44");

        verify(customerService).getByPhoneNumberPrefix("44");

        assertEquals("First name", customerDtos.get(0).getFirstName());
        assertEquals("Last name", customerDtos.get(0).getLastName());
        assertEquals("123", customerDtos.get(0).getPhoneNumber());
    }

}