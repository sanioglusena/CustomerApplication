package com.delivery.customer.validator;

import com.delivery.customer.exception.CustomerServiceException;
import com.delivery.customer.request.CustomerCreateRequest;
import com.delivery.customer.service.AddressService;
import com.delivery.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerServiceValidator {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MessageSource messageSource;

    private static final String CUSTOMER_EXISTS = "customer.exists";
    private static final String CUSTOMER_NOT_FOUND = "customer.not.found";
    private static final String ADDRESS_NOT_FOUND = "address.not.found";

    /**
     * Checks duplicate customers by first name, last name and phone number
     *
     * @param customerCreateRequest
     */
    public void validateCreateCustomerRequest(CustomerCreateRequest customerCreateRequest) {

        Optional.of(customerService.existByFirstNameAndLastNameAndPhoneNumber(customerCreateRequest.getFirstName(), customerCreateRequest.getLastName(), customerCreateRequest.getPhoneNumber()))
                .filter(Boolean.TRUE::equals)
                .map(exists -> {
                    throw new CustomerServiceException(messageSource.getMessage(CUSTOMER_EXISTS, null, null));
                });
    }

    /**
     * Checks if customer exists in database
     *
     * @param customerId
     */
    public void validateCustomerExists(Long customerId) {

        Optional.of(customerService.existsById(customerId))
                .filter(Boolean.FALSE::equals)
                .map(exists -> {
                    throw new CustomerServiceException(messageSource.getMessage(CUSTOMER_NOT_FOUND, null, null));
                });

    }

    /**
     * Checks if customer exists in database
     * Checks if address exists in database
     *
     * @param customerId
     */
    public void validateDeleteAddressRequest(Long customerId, Long addressId) {

        validateCustomerExists(customerId);

        Optional.of(addressService.existsById(addressId))
                .filter(Boolean.FALSE::equals)
                .map(exists -> {
                    throw new CustomerServiceException(messageSource.getMessage(ADDRESS_NOT_FOUND, null, null));
                });
    }
}
