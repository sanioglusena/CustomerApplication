package com.delivery.customer.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@Builder
@Data
public class CustomerCreateRequest {

    @NotBlank(message = "First name is mandatory")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    private String lastName;

    @Pattern(regexp = "^\\+*(?:[0-9] ?){6,14}[0-9]$", message = "Please enter a valid phone number! Phone number can start with + sign and contain only numbers and space")
    private String phoneNumber;

    @Valid
    private List<AddressCreateRequest> addresses;

}
