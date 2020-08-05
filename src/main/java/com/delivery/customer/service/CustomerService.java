package com.delivery.customer.service;

import com.delivery.customer.request.AddressCreateRequest;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.response.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<CustomerDto> getAllCustomers();

    CustomerDto getCustomerById(Long customerId);

    void createCustomer(CustomerCreateRequest customerCreateRequest);

    void createAddressForCustomer(AddressCreateRequest addressCreateRequest, Long customerId);

    void deleteAddressById(Long addressId);

    Boolean existByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);

    Boolean existsById(Long id);

    List<CustomerDto> getByCityName(String cityName);

    List<CustomerDto> getByPhoneNumberPrefix(String phoneNumberPrefix);
}
