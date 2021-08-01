package com.getir.readingisgood.model.response;

import com.getir.readingisgood.entity.enums.OrderStatusType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderResponse {

    private Long id;
    private OrderStatusType status;
    private Long quantity;
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private BookResponse book;
    private CustomerResponse customer;
}
