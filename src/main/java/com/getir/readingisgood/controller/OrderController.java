package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.OrderResponse;
import com.getir.readingisgood.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @PostMapping
    public ResponseEntity<GenericReturnValue<Long>> createOrder(@RequestBody OrderRequest orderRequest) {
        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @PreAuthorize("hasRole('CUSTOMER')")
    @PutMapping
    public ResponseEntity<GenericReturnValue<String>> cancelOrder(@RequestBody Long orderId) {
        return ResponseEntity.ok(orderService.cancelOrder(orderId));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<GenericReturnValue<Page<OrderResponse>>> getOrders(@RequestParam(defaultValue = "0") Integer pageNo,
                                                                             @RequestParam(defaultValue = "10") Integer pageSize,
                                                                             @RequestParam(defaultValue = "id") String sortBy) {
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return ResponseEntity.ok(orderService.getOrders(paging));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/interval")
    public ResponseEntity<GenericReturnValue<Page<OrderResponse>>> getOrdersWithDateInterval(@RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date startDate,
                                                                                             @RequestParam @DateTimeFormat(pattern = "yyyy-MM-dd") Date endDate,
                                                                                             @RequestParam(defaultValue = "0") Integer pageNo,
                                                                                             @RequestParam(defaultValue = "10") Integer pageSize) {
        Pageable paging = PageRequest.of(pageNo, pageSize);
        return ResponseEntity.ok(orderService.getOrdersWithDateInterval(startDate, endDate, paging));
    }
}
