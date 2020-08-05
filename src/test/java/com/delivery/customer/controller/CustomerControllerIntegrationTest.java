package com.delivery.customer.controller;

import com.delivery.customer.CustomerApplication;
import com.delivery.customer.repository.CustomerRepository;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.response.CustomerDto;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CustomerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class CustomerControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Should create new customer.
     *
     * @throws Exception
     */
    @Test
    public void shouldCreateCustomer() throws Exception {

        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .firstName("First name")
                .lastName("Surname")
                .phoneNumber("90 555 333 22 11")
                .addresses(Collections.emptyList())
                .build();

        mvc.perform(post("/customer")
                .content(mapper.writeValueAsString(customerCreateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        Boolean exists = customerRepository.existsByFirstNameAndLastNameAndPhoneNumber("First name", "Surname", "90 555 333 22 11");

        assertEquals(exists, true);
    }

    /**
     * Checks validation errors while creating customer.
     *
     * @throws Exception
     */
    @Test
    public void shouldValidateCreateCustomer() throws Exception {

        CustomerCreateRequest customerCreateRequest = CustomerCreateRequest.builder()
                .firstName("")
                .lastName("")
                .phoneNumber("")
                .addresses(Collections.emptyList())
                .build();

        mvc.perform(post("/customer")
                .content(mapper.writeValueAsString(customerCreateRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    /**
     * Should return customers in database.
     * Initial data.sql inserts 3 customers in database.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetCustomers() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/customer")
                .contentType(MediaType.APPLICATION_JSON))
                .andReturn();

        List<CustomerDto> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(3, customers.size());
    }

    /**
     * Should receive customer by id.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetCustomerById() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        CustomerDto customer = mapper.readValue(mvcResult.getResponse().getContentAsString(), CustomerDto.class);

        assertEquals("Harry", customer.getFirstName());
        assertEquals("Potter", customer.getLastName());
    }

    /**
     * Checks the case when user requests a customer which does not exist in database.
     * Checks status is bad request.
     * Checks request path and error message are available in response.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnBadRequestErrorWhenCustomerIsNotFound() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/customer/100")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();

        Map<String, Object> response = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals("/customer/100", response.get("path"));
        assertEquals("Customer not found!", response.get("message"));
    }

}