package com.delivery.customer.mapper;

import com.delivery.customer.entity.Customer;
import com.delivery.customer.response.AddressDto;
import com.delivery.customer.response.CustomerDto;

import java.util.List;
import java.util.stream.Collectors;

public class CustomerMapper {

    public static CustomerDto map(Customer customer) {

        List<AddressDto> addresses = customer.getAddresses()
                .stream()
                .map(AddressMapper::map)
                .collect(Collectors.toList());

        return CustomerDto.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .phoneNumber(customer.getPhoneNumber())
                .addresses(addresses)
                .build();
    }
}
