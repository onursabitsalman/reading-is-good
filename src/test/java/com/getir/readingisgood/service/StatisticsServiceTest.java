package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.response.BookResponse;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.MonthlyStatisticResponse;
import com.getir.readingisgood.model.response.OrderResponse;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StatisticsServiceTest {

    @InjectMocks
    private StatisticsService statisticsService;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private OrderMapper orderMapper;

    @Mock
    private UserDetailsDaoService userDetailsDaoService;

    @Test
    public void getMonthlyStatistics_successfully() {
        Order order = Order.builder()
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .build();
        List<Order> orders = new ArrayList<>();
        orders.add(order);

        BookResponse bookResponse = BookResponse.builder()
                .id(1L)
                .author("Onur Sabit Salman")
                .name("Effective Java")
                .page(500L)
                .price(19.99)
                .stock(100L)
                .build();

        OrderResponse orderResponse = OrderResponse.builder()
                .quantity(10L)
                .status(OrderStatusType.PURCHASED)
                .createdDate(LocalDateTime.now())
                .book(bookResponse)
                .build();
        List<OrderResponse> ordersResponse = new ArrayList<>();
        ordersResponse.add(orderResponse);

        int monthNumber = orderResponse.getCreatedDate().get(ChronoField.MONTH_OF_YEAR);
        String monthName = Month.of(monthNumber).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);

        MonthlyStatisticResponse monthlyStatisticResponse = MonthlyStatisticResponse.builder()
                .monthName(monthName)
                .totalBookCount(10L)
                .totalOrderCount(1)
                .totalPurchasedAmount(19.99)
                .build();
        List<MonthlyStatisticResponse> monthlyStatistics = new ArrayList<>();
        monthlyStatistics.add(monthlyStatisticResponse);

        when(userDetailsDaoService.getUserId()).thenReturn(1L);
        when(orderRepository.findAllByStatusAndCustomerId(eq(OrderStatusType.PURCHASED), anyLong())).thenReturn(orders);
        when(orderMapper.toOrderResponses(anyList())).thenReturn(ordersResponse);

        GenericReturnValue<List<MonthlyStatisticResponse>> result = statisticsService.getMonthlyStatistics();
        assertEquals(new GenericReturnValue<>(monthlyStatistics).getValue().size(), result.getValue().size());
    }

}