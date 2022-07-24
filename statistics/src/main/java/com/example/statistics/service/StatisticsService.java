package com.example.statistics.service;

import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;

import java.util.List;

/**
 * Order service contracts.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public interface StatisticsService {

    List<MonthlyReportDto> getMonthlyOrderStatisticsForCustomer(long customerId) throws StatisticsValidationException, StatisticsProcessException;
}
