package com.getir.readingisgood.model.modelmapper;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.model.request.OrderRequest;
import com.getir.readingisgood.model.response.CustomerOrderResponse;
import com.getir.readingisgood.model.response.OrderResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {BookMapper.class, UserMapper.class})
public interface OrderMapper {
    Order toEntity(OrderRequest orderRequest);
    CustomerOrderResponse toCustomerOrderResponse(Order order);
    OrderResponse toOrderResponse(Order order);
    List<OrderResponse> toOrderResponses(List<Order> orders);
}
