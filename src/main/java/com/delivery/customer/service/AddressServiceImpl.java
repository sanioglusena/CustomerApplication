package com.delivery.customer.service;

import com.delivery.customer.entity.Address;
import com.delivery.customer.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public void saveAll(List<Address> addresses) {
        addressRepository.saveAll(addresses);
    }

    @Override
    public Boolean existsById(Long id) {
        return addressRepository.existsById(id);
    }

    @Modifying
    @Override
    public void deleteById(Long id) {
        addressRepository.deleteById(id);
    }
}
