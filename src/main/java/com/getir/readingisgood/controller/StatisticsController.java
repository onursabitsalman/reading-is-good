package com.getir.readingisgood.controller;

import com.getir.readingisgood.model.response.GenericReturnValue;
import com.getir.readingisgood.model.response.MonthlyStatisticResponse;
import com.getir.readingisgood.service.StatisticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/statistic")
@RequiredArgsConstructor
public class StatisticsController {

    private final StatisticsService statisticsService;

    @PreAuthorize("hasRole('CUSTOMER')")
    @GetMapping("/monthly")
    public ResponseEntity<GenericReturnValue<List<MonthlyStatisticResponse>>> getMonthlyStatistics() {
        return ResponseEntity.ok(statisticsService.getMonthlyStatistics());
    }

}
