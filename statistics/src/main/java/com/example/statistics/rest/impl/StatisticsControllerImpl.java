package com.example.statistics.rest.impl;

import com.example.statistics.rest.StatisticsController;
import com.example.statistics.service.StatisticsService;
import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implementation of {@link StatisticsController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@RestController
public class StatisticsControllerImpl implements StatisticsController {

    private StatisticsService statisticsService;

    @Autowired
    public StatisticsControllerImpl(StatisticsService statisticsService) {
        this.statisticsService = statisticsService;
    }

    @Override
    public ResponseEntity<List<MonthlyReportDto>> getMonthlyOrderStatisticsForCustomer(long customerId) throws StatisticsValidationException, StatisticsProcessException {
        List<MonthlyReportDto> statistics = statisticsService.getMonthlyOrderStatisticsForCustomer(customerId);
        return new ResponseEntity<>(statistics, HttpStatus.OK);
    }
}
