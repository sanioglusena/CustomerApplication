package com.delivery.customer.service;

import com.delivery.customer.entity.Address;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    void saveAll(List<Address> addresses);

    Boolean existsById(Long id);

    void deleteById(Long addressId);
}
