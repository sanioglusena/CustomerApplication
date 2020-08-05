package com.delivery.customer.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Builder
@Data
public class AddressCreateRequest {

    @Pattern(regexp = "^(HOME|WORK|SCHOOL)+$", message = "Accepted values are: HOME, SCHOOL, WORK")
    private String addressType;

    @NotBlank(message = "City is mandatory")
    private String city;

    @NotBlank(message = "Country is mandatory")
    private String country;

    @NotBlank(message = "Address line is mandatory")
    private String addressLine;
}
