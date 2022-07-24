package com.example.statistics.rest;

import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Controller contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RequestMapping(value = "statistics/v1")
public interface StatisticsController {

    @GetMapping(value = "status")
    ResponseEntity<String> status();

    @GetMapping(value = "monthlyCustomerOrders")
    ResponseEntity<List<MonthlyReportDto>> getMonthlyOrderStatisticsForCustomer(@RequestParam(name = "customerId") long customerId) throws StatisticsValidationException, StatisticsProcessException;

}
