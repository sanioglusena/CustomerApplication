package com.delivery.customer.service;

import com.delivery.customer.response.CustomerDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchService {

    List<CustomerDto> findCustomersByCity(String cityName);

    List<CustomerDto> findCustomersByPhoneNumberPrefix(String phoneNumberPrefix);

}
