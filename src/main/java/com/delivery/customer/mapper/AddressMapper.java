package com.delivery.customer.mapper;

import com.delivery.customer.entity.Address;
import com.delivery.customer.entity.AddressType;
import com.delivery.customer.request.AddressCreateRequest;
import com.delivery.customer.response.AddressDto;

public class AddressMapper {

    public static AddressDto map(Address address) {
        return AddressDto.builder()
                .id(address.getId())
                .city(address.getCity())
                .country(address.getCountry())
                .addressLine(address.getAddressLine())
                .addressType(address.getAddressType())
                .build();
    }

    public static Address map(AddressCreateRequest address, Long customerId) {
        return Address.builder()
                .customerId(customerId)
                .addressLine(address.getAddressLine())
                .addressType(AddressType.valueOf(address.getAddressType()))
                .city(address.getCity())
                .country(address.getCountry())
                .build();
    }
}
