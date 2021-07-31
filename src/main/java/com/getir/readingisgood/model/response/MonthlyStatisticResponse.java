package com.getir.readingisgood.model.response;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MonthlyStatisticResponse {

    private String monthName;
    private int totalOrderCount;
    private Long totalBookCount;
    private Double totalPurchasedAmount;

}
