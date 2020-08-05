package com.delivery.customer.repository;

import com.delivery.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    Boolean existsByFirstNameAndLastNameAndPhoneNumber(String firstName, String lastName, String phoneNumber);

    @Query(value = "select distinct(c.*) from Customer c, Address a where a.customer_id = c.id and UPPER(a.city) =:cityName", nativeQuery = true)
    List<Customer> findByCityName(String cityName);

    List<Customer> findByPhoneNumberStartingWith(String phoneNumberPrefix);
}
