package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.CustomerRequest;
import com.getir.readingisgood.model.response.CustomerOrderResponse;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @PreAuthorize("permitAll()")
    @PostMapping
    public ResponseEntity<GenericReturnValue<Long>> createCustomer(@RequestBody CustomerRequest customerRequest) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequest));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/order")
    public ResponseEntity<GenericReturnValue<Page<CustomerOrderResponse>>> getCustomerOrders(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                                             @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(customerService.getCustomerOrders(paging));
    }

}
