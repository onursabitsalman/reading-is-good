package com.getir.readingisgood.entity;

import com.getir.readingisgood.entity.enums.OrderStatusType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "t_order")
public class Order extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id")
    @SequenceGenerator(name = "order_id", sequenceName = "order_id_sequence", allocationSize = 100)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private User customer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    private Book book;

    @Min(value = 1, message = "Quantity should be greater than 0!")
    @NotNull(message = "Quantity cannot be null!")
    private Long quantity;

    @NotNull(message = "Order status cannot be null!")
    @Enumerated(EnumType.STRING)
    private OrderStatusType status;
}
