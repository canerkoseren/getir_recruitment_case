package com.example.statistics.service.impl;

import com.example.statistics.rest.configuration.client.RestClient;
import com.example.statistics.service.StatisticsService;
import com.example.statistics.service.model.CustomerDto;
import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.OrderDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test implementation of {@link StatisticsService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 28.7.2022
 */
public class StatisticsServiceTest {

    private static final String FIND_CUSTOMER_BY_ID_URL = "findCustomerByIdUrl";
    private static final String CUSTOMER_MONTHLY_REPORT_URL = "customerMonthlyReportUrl";

    private static final Long CUSTOMER_ID_WITH_ORDER = 9876L;
    private static final Long CUSTOMER_ID_WITH_NO_ORDER = 12345L;

    private static RestClient restClient;
    private static StatisticsService service;

    private static CustomerDto customer;
    private static OrderDto order;

    @BeforeAll
    static void setUp() {

        customer = new CustomerDto();
        customer.setId(CUSTOMER_ID_WITH_ORDER);

        order = new OrderDto();
        order.setCustomerId(customer.getId());
        order.setBookIdList(Collections.singletonList(8899L));
        order.setStatus("OK");
        order.setProcessDate(LocalDate.now());
        order.setAmount(999D);

        RestTemplate restTemplate = mock(RestTemplate.class);

        restClient = mock(RestClient.class);
        when(restClient.restTemplate()).thenReturn(restTemplate);
        when(restTemplate.getForObject(eq(FIND_CUSTOMER_BY_ID_URL + customer.getId()), eq(CustomerDto.class))).thenReturn(customer);
        when(restTemplate.getForObject(eq(FIND_CUSTOMER_BY_ID_URL + CUSTOMER_ID_WITH_NO_ORDER), eq(CustomerDto.class))).thenReturn(new CustomerDto());
        when(restTemplate.getForObject(eq(CUSTOMER_MONTHLY_REPORT_URL + customer.getId()), eq(OrderDto[].class))).thenReturn(new OrderDto[]{order});

        service = new StatisticsServiceImpl(restClient);
        ReflectionTestUtils.setField(service, "findCustomerByIdUrl", FIND_CUSTOMER_BY_ID_URL);
        ReflectionTestUtils.setField(service, "customerMonthlyReportUrl", CUSTOMER_MONTHLY_REPORT_URL);
    }

    @Test
    void when_CustomerIsNotFound_ThrowException() {

        StatisticsValidationException exception = Assertions.assertThrows(StatisticsValidationException.class, () -> {
            service.getMonthlyOrderStatisticsForCustomer(88L);
        });
        Assertions.assertNotNull(exception);
    }

    @Test
    void when_OrderIsNotFound_ThrowException() {

        StatisticsProcessException exception = Assertions.assertThrows(StatisticsProcessException.class, () -> {
            service.getMonthlyOrderStatisticsForCustomer(CUSTOMER_ID_WITH_NO_ORDER);
        });
        Assertions.assertNotNull(exception);
    }

    @Test
    void testGetMonthlyOrderStatisticsForCustomer() throws StatisticsValidationException, StatisticsProcessException {

        List<MonthlyReportDto> report = service.getMonthlyOrderStatisticsForCustomer(customer.getId());
        Assertions.assertNotNull(report);
    }
}
