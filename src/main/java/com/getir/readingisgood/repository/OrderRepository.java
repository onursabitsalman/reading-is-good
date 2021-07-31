package com.getir.readingisgood.repository;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Page<Order> findAllByCustomerId(Long id, Pageable pageable);

    Page<Order> findAllByCreatedDateIsBetween(LocalDateTime dt1, LocalDateTime dt2, Pageable pageable);

    List<Order> findAllByStatusAndCustomerId(OrderStatusType orderStatusType, Long id);

    Optional<Order> findByIdAndCustomerId(Long id, Long customerId);

    @Modifying
    @Query("UPDATE Order o SET o.status = ?2 WHERE o.id = ?1")
    int updateOrderStatus(Long id, OrderStatusType orderStatusType);

}
