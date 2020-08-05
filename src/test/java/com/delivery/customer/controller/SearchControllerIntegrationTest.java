package com.delivery.customer.controller;

import com.delivery.customer.CustomerApplication;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK, classes = CustomerApplication.class)
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-integrationtest.properties")
public class SearchControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper mapper;

    /**
     * Checks get customers by city name method.
     * Initial test database has 1 people from London.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetCustomersByCityName() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/search/city/London")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerDto> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(1, customers.size());
    }

    /**
     * Checks the case when there is no customer from the city.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnAnEmptyListWhenThereIsNoCustomerFromTheCity() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/search/city/Paris")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerDto> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(0, customers.size());
    }

    /**
     * Checks get customers by phone prefix method.
     * Initial test database has 2 people with phone starting with 44.
     *
     * @throws Exception
     */
    @Test
    public void shouldGetCustomersByPhonePrefix() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/search/phone/44")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerDto> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(2, customers.size());
    }

    /**
     * Checks the case when there is no phone number with the prefix.
     *
     * @throws Exception
     */
    @Test
    public void shouldReturnAnEmptyListWhenThereIsNoPhoneNumberWithThePrefix() throws Exception {

        MvcResult mvcResult = mvc.perform(get("/search/phone/55")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        List<CustomerDto> customers = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<>() {
        });

        assertEquals(0, customers.size());
    }
}