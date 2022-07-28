# Project

## 1. Project Details
This repository has 4 different applications. Each of them is a sub module
in the project repository.

### 1.1 Book Application

This application is responsible for book domain.

Exposed endpoints:

* ***/status*** returns the application is ready.
* ***/save*** saves a new book in mongodb.  
* ***/findById*** finds the book from mongodb by id.
* ***/update*** updates the given book record.
* ***/stocks*** fetches stock counts of each book.

### 1.2 Customer Application

This application is responsible for customer domain.

Exposed endpoints:

* ***/save*** saves a new customer in mongodb.
* ***/findById*** finds the customer from mongodb by id.
* ***/findByEmail*** finds the customer from mongodb by email.

### 1.3 Order Application

This application is responsible for customer domain.

Exposed endpoints:

## Technology Stack
* Java - version 11
* Spring Boot - version 2.6.1
* Mongodb