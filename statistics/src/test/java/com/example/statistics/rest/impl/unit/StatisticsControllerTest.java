package com.example.statistics.rest.impl.unit;

import com.example.statistics.rest.StatisticsController;
import com.example.statistics.rest.impl.StatisticsControllerImpl;
import com.example.statistics.service.StatisticsService;
import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation for {@link StatisticsController}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 28.7.2022
 */
class StatisticsControllerTest {

    @Test
    void testGetMonthlyOrderStatisticsForCustomer() throws StatisticsValidationException, StatisticsProcessException {

        List<MonthlyReportDto> report = new ArrayList<>();

        StatisticsService statisticsService = mock(StatisticsService.class);
        when(statisticsService.getMonthlyOrderStatisticsForCustomer(anyLong())).thenReturn(report);

        StatisticsController controller = new StatisticsControllerImpl(statisticsService);
        ResponseEntity<List<MonthlyReportDto>> result = controller.getMonthlyOrderStatisticsForCustomer(9L);
        Assertions.assertNotNull(result);
    }
}
