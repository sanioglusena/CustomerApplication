package com.delivery.customer.service;

import com.delivery.customer.configuration.CacheKeys;
import com.delivery.customer.entity.Address;
import com.delivery.customer.entity.Customer;
import com.delivery.customer.exception.CustomerServiceException;
import com.delivery.customer.mapper.AddressMapper;
import com.delivery.customer.mapper.CustomerMapper;
import com.delivery.customer.repository.CustomerRepository;
import com.delivery.customer.request.AddressCreateRequest;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.response.CustomerDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MessageSource messageSource;

    private static final String CUSTOMER_NOT_FOUND = "customer.not.found";

    @Override
    public List<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public CustomerDto getCustomerById(Long customerId) {
        return customerRepository.findById(customerId)
                .map(CustomerMapper::map)
                .orElseThrow(() -> new CustomerServiceException(messageSource.getMessage(CUSTOMER_NOT_FOUND, null, null)));
    }

    /**
     * Creates new customer with addresses.
     * Evicts CUSTOMERS_BY_CITY cache.
     *
     * @param customerCreateRequest
     */
    @CacheEvict(value = CacheKeys.CUSTOMERS_BY_CITY, allEntries = true)
    @Transactional
    @Override
    public void createCustomer(CustomerCreateRequest customerCreateRequest) {

        Customer customer = Customer.builder()
                .firstName(customerCreateRequest.getFirstName())
                .lastName(customerCreateRequest.getLastName())
                .phoneNumber(customerCreateRequest.getPhoneNumber())
                .build();

        Customer savedCustomer = customerRepository.save(customer);

        List<Address> addresses = customerCreateRequest.getAddresses().stream().map(addressCreateRequest -> AddressMapper.map(addressCreateRequest, savedCustomer.getId())).collect(Collectors.toList());

        addressService.saveAll(addresses);
    }

    /**
     * Creates new address for existing customer.
     * Evicts CUSTOMERS_BY_CITY cache.
     *
     * @param addressCreateRequest
     * @param customerId
     */
    @CacheEvict(value = CacheKeys.CUSTOMERS_BY_CITY, allEntries = true)
    @Modifying
    @Override
    public void createAddressForCustomer(AddressCreateRequest addressCreateRequest, Long customerId) {
        Customer customer = customerRepository.findById(customerId).orElseThrow(() -> new CustomerServiceException(messageSource.getMessage(CUSTOMER_NOT_FOUND, null, null)));

        Address address = AddressMapper.map(addressCreateRequest, customerId);

        customer.getAddresses().add(address);

        customerRepository.save(customer);
    }

    /**
     * Deletes address frome existing customer.
     * Evicts CUSTOMERS_BY_CITY cache.
     *
     * @param addressId
     */
    @CacheEvict(value = CacheKeys.CUSTOMERS_BY_CITY, allEntries = true)
    @Modifying
    @Override
    public void deleteAddressById(Long addressId) {
        addressService.deleteById(addressId);
    }

    @Override
    public Boolean existByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber) {
        return customerRepository.existsByFirstNameAndLastNameAndPhoneNumber(firstName, lastName, phoneNumber);
    }

    @Override
    public Boolean existsById(Long id) {
        return customerRepository.existsById(id);
    }

    @Override
    public List<CustomerDto> getByCityName(String cityName) {
        return customerRepository.findByCityName(cityName.toUpperCase())
                .stream()
                .map(CustomerMapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public List<CustomerDto> getByPhoneNumberPrefix(String phoneNumberPrefix) {
        return customerRepository.findByPhoneNumberStartingWith(phoneNumberPrefix)
                .stream()
                .map(CustomerMapper::map)
                .collect(Collectors.toList());
    }
}
