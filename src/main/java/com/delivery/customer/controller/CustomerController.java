package com.delivery.customer.controller;

import com.delivery.customer.request.AddressCreateRequest;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.response.CustomerDto;
import com.delivery.customer.service.CustomerService;
import com.delivery.customer.validator.CustomerServiceValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private CustomerServiceValidator customerServiceValidator;

    @Autowired
    private CustomerService customerService;

    @PostMapping
    public void createCustomer(@RequestBody @Valid CustomerCreateRequest customerCreateRequest) {
        customerServiceValidator.validateCreateCustomerRequest(customerCreateRequest);
        customerService.createCustomer(customerCreateRequest);
    }

    @GetMapping
    public List<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public CustomerDto getCustomerById(@PathVariable("id") Long customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping("/{id}/address")
    public void createAddressForCustomer(@PathVariable("id") Long customerId, @RequestBody @Valid AddressCreateRequest addressCreateRequest) {
        customerServiceValidator.validateCustomerExists(customerId);
        customerService.createAddressForCustomer(addressCreateRequest, customerId);
    }

    @DeleteMapping("/{id}/address/{addressId}")
    public void deleteAddressFromCustomer(@PathVariable("id") Long customerId, @PathVariable("addressId") Long addressId) {
        customerServiceValidator.validateDeleteAddressRequest(customerId, addressId);
        customerService.deleteAddressById(addressId);
    }
}
