package com.example.order.data.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

/**
 * Order Entity.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
@Document("order")
public class Order {

    @Id
    private Long id;
    private Long customerId;
    private List<Long> bookIdList;
    private String status;
    private Double amount;
    private LocalDate processDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public List<Long> getBookIdList() {
        return bookIdList;
    }

    public void setBookIdList(List<Long> bookIdList) {
        this.bookIdList = bookIdList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getProcessDate() {
        return processDate;
    }

    public void setProcessDate(LocalDate processDate) {
        this.processDate = processDate;
    }
}
