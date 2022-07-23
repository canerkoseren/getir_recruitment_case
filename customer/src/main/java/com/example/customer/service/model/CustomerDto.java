package com.example.customer.service.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@NoArgsConstructor
public class CustomerDto {

    @Id
    private String email;
    private String name;
    private String lastName;
}
