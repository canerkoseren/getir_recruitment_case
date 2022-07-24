package com.example.statistics.service.model;

import java.math.BigDecimal;

public class MonthlyReportDto {

    private int month;
    private Long totalOrderCount;
    private Long totalBookCount;
    private Double totalPurchasedAmount;

    public MonthlyReportDto() {
    }

    public MonthlyReportDto(int month, Long totalOrderCount, Long totalBookCount, Double totalPurchasedAmount) {
        this.month = month;
        this.totalOrderCount = totalOrderCount;
        this.totalBookCount = totalBookCount;
        this.totalPurchasedAmount = totalPurchasedAmount;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public Long getTotalOrderCount() {
        return totalOrderCount;
    }

    public void setTotalOrderCount(Long totalOrderCount) {
        this.totalOrderCount = totalOrderCount;
    }

    public Long getTotalBookCount() {
        return totalBookCount;
    }

    public void setTotalBookCount(Long totalBookCount) {
        this.totalBookCount = totalBookCount;
    }

    public Double getTotalPurchasedAmount() {
        return totalPurchasedAmount;
    }

    public void setTotalPurchasedAmount(Double totalPurchasedAmount) {
        this.totalPurchasedAmount = totalPurchasedAmount;
    }
}
