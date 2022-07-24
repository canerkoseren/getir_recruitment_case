package com.example.statistics.service.model;

/**
 * Customer dto class.
 *
 * @author Caner Köseren
 * @version 0.0.1
 * @created 24.7.2022
 */
public class CustomerDto {

    private Long id;
    private String email;
    private String name;
    private String lastName;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
