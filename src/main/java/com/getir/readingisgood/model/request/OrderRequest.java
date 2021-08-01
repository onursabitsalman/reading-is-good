package com.getir.readingisgood.model.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderRequest {

    @NotNull(message = "Order status cannot be null!")
    private Long bookId;

    @JsonIgnore
    private Long customerId;

    @JsonIgnore
    @Enumerated(EnumType.STRING)
    private OrderStatusType status = OrderStatusType.PURCHASED;

    @Min(value = 1, message = "Quantity should be greater than 0!")
    @NotNull(message = "Quantity cannot be null!")
    private Long quantity;
}
