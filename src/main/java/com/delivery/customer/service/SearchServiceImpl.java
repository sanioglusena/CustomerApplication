package com.delivery.customer.service;

import com.delivery.customer.configuration.CacheKeys;
import com.delivery.customer.response.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchServiceImpl implements SearchService {

    @Autowired
    private CustomerService customerService;

    /**
     * Gets all customers from this city
     * Checks cache first. If it does not exist in cache then retrieves from database
     *
     * @param cityName
     * @return List<CustomerDto>
     */
    @Cacheable(cacheNames = CacheKeys.CUSTOMERS_BY_CITY)
    @Override
    public List<CustomerDto> findCustomersByCity(String cityName) {
        return customerService.getByCityName(cityName);
    }

    /**
     * Gets all customers starting with the prefix phone number
     *
     * @param phoneNumberPrefix
     * @return List<CustomerDto>
     */
    @Override
    public List<CustomerDto> findCustomersByPhoneNumberPrefix(String phoneNumberPrefix) {
        return customerService.getByPhoneNumberPrefix(phoneNumberPrefix);
    }
}
