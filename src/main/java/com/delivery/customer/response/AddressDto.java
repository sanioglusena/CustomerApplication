package com.delivery.customer.response;

import com.delivery.customer.entity.AddressType;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AddressDto {

    private long id;

    private AddressType addressType;

    private String city;

    private String country;

    private String addressLine;

}
