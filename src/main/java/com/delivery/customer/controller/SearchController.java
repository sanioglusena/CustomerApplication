package com.delivery.customer.controller;

import com.delivery.customer.response.CustomerDto;
import com.delivery.customer.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    @GetMapping("city/{name}")
    public List<CustomerDto> getCustomerByCityName(@PathVariable("name") String cityName) {
        return searchService.findCustomersByCity(cityName);
    }

    @GetMapping("phone/{prefix}")
    public List<CustomerDto> getCustomerByPrefix(@PathVariable("prefix") String prefix) {
        return searchService.findCustomersByPhoneNumberPrefix(prefix);
    }
}
