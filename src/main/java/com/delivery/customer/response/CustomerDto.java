package com.delivery.customer.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class CustomerDto {

    private long id;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private List<AddressDto> addresses;

}
