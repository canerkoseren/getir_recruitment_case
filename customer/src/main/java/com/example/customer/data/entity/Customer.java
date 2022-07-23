package com.example.customer.data.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document("customer")
public class Customer {

    @Id
    private Long id;
    private String email;
    private String name;
    private String lastName;
}
