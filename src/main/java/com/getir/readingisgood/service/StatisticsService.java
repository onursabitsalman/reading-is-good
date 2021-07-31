package com.getir.readingisgood.service;

import com.getir.readingisgood.entity.Order;
import com.getir.readingisgood.entity.enums.OrderStatusType;
import com.getir.readingisgood.model.modelmapper.OrderMapper;
import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.MonthlyStatisticResponse;
import com.getir.readingisgood.model.response.OrderResponse;
import com.getir.readingisgood.repository.OrderRepository;
import com.getir.readingisgood.security.service.UserDetailsDaoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DecimalFormat;
import java.time.Month;
import java.time.format.TextStyle;
import java.time.temporal.ChronoField;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

@Slf4j
@Service
@RequiredArgsConstructor
public class StatisticsService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;
    private final UserDetailsDaoService userDetailsDaoService;

    @Transactional(readOnly = true)
    public GenericReturnValue<List<MonthlyStatisticResponse>> getMonthlyStatistics() {

        List<MonthlyStatisticResponse> monthlyStatistics = new ArrayList<>();
        Long currentCustomerId = userDetailsDaoService.getUserId();
        List<Order> purchasedOrders = orderRepository.findAllByStatusAndCustomerId(OrderStatusType.PURCHASED, currentCustomerId);
        List<OrderResponse> purchasedOrderResponse = orderMapper.toOrderResponses(purchasedOrders);

        DecimalFormat twoDigitsFormat = new DecimalFormat("#.##");

        Map<Integer, List<OrderResponse>> monthlyOrders = purchasedOrderResponse.stream()
                .collect(groupingBy(d -> d.getCreatedDate().get(ChronoField.MONTH_OF_YEAR)));

        for (var entry : monthlyOrders.entrySet()) {

            String monthName = Month.of(entry.getKey()).getDisplayName(TextStyle.FULL_STANDALONE, Locale.ENGLISH);
            long totalBookCount = 0L;
            double totalPurchasedAmount = 0.0;

            for (OrderResponse orderResponse : entry.getValue()) {
                totalBookCount += orderResponse.getQuantity();
                totalPurchasedAmount += orderResponse.getBook().getPrice() * orderResponse.getQuantity();
            }

            monthlyStatistics.add(
                    MonthlyStatisticResponse.builder()
                            .monthName(monthName)
                            .totalOrderCount(entry.getValue().size())
                            .totalBookCount(totalBookCount)
                            .totalPurchasedAmount(Double.valueOf(twoDigitsFormat.format(totalPurchasedAmount)))
                            .build()
            );
        }

        return new GenericReturnValue<>(monthlyStatistics);
    }
}
