package com.example.statistics.service.impl;

import com.example.statistics.rest.configuration.client.RestClient;
import com.example.statistics.service.StatisticsService;
import com.example.statistics.service.model.CustomerDto;
import com.example.statistics.service.model.MonthlyReportDto;
import com.example.statistics.service.model.OrderDto;
import com.example.statistics.service.model.exception.StatisticsProcessException;
import com.example.statistics.service.model.exception.StatisticsValidationException;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Service implementation of {@link StatisticsService}.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Service
public class StatisticsServiceImpl implements StatisticsService {

    private static final Logger logger = LoggerFactory.getLogger(StatisticsServiceImpl.class);


    @Getter
    @Setter
    @Value("${statistics.app.findCustomerByIdUrl}")
    private String findCustomerByIdUrl;

    @Getter
    @Setter
    @Value("${statistics.app.customerMonthlyReportUrl}")
    private String customerMonthlyReportUrl;

    private RestTemplate restTemplate;

    @Autowired
    public StatisticsServiceImpl(RestClient restClient) {
        this.restTemplate = restClient.restTemplate();
    }

    @Override
    public List<MonthlyReportDto> getMonthlyOrderStatisticsForCustomer(long customerId) throws StatisticsValidationException, StatisticsProcessException {

        if (customerId == 0) {
            throw new StatisticsValidationException("customerId can not be null");
        }

        checkCustomer(customerId);

        List<MonthlyReportDto> report = new ArrayList<>();

        String url = customerMonthlyReportUrl + customerId;
        OrderDto[] orders = restTemplate.getForObject(url, OrderDto[].class);

        if (orders == null || orders.length == 0) {
            throw new StatisticsProcessException("No data fetched from Order service");
        }

        List<OrderDto> orderList = Arrays.asList(orders);


        Map<Integer, Long> monthlyOrderCount = orderList.stream()
                .filter(order -> order.getStatus().equals("OK"))
                .collect(
                        Collectors.groupingBy(OrderDto::getMonth, Collectors.counting()));

        Map<Integer, Double> monthlyAmount = orderList.stream()
                .filter(order -> order.getStatus().equals("OK"))
                .collect(
                        Collectors.groupingBy(OrderDto::getMonth,
                                Collectors.summingDouble(OrderDto::getAmount)));

        Map<Integer, Long> monthlyBookCounts = orderList.stream()
                .filter(order -> order.getStatus().equals("OK"))
                .collect(
                        Collectors.groupingBy(OrderDto::getMonth,
                                Collectors.summingLong(order -> order.getBookIdList().size())));

        monthlyOrderCount.forEach((month, count) -> {

            Double amount = monthlyAmount.get(month);
            Long bookCount = monthlyBookCounts.get(month);
            report.add(new MonthlyReportDto(month, count, bookCount, amount));
        });

        return report;
    }

    private boolean checkCustomer(Long customerId) throws StatisticsProcessException, StatisticsValidationException {

        String url = findCustomerByIdUrl + customerId;

        try {
            CustomerDto customer = restTemplate.getForObject(url, CustomerDto.class);
            if (customer == null) {
                throw new StatisticsValidationException("Customer can not be found by customerId: " + customerId);
            }
            return true;
        } catch (HttpClientErrorException ex) {
            throw new StatisticsProcessException("HttpClientError while reading customer ", ex);
        }
    }
}
