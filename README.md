# Project

## 1. Project Details
This repository has 4 different applications. Each of them is a sub module
in the project repository.

### 1.1 Book Application

This application is responsible for book domain.

Exposed endpoints:

* ***/status*** returns the application is ready.
* ***/save*** saves a new book.  
* ***/findById*** finds the book by id.
* ***/update*** updates the given book record.
* ***/stocks*** fetches stock counts of each book.

### 1.2 Customer Application

This application is responsible for customer domain.

Exposed endpoints:

* ***/save*** saves a new customer.
* ***/findById*** finds the customer by id.
* ***/findByEmail*** finds the customer by email.

### 1.3 Order Application

This application is responsible for customer domain.

Exposed endpoints:

* ***/save*** saves a new order.
* ***/findById*** finds an order by id.
* ***/update*** updates the defined order.
* ***/queryByProcessDate*** queries orders between two dates.
* ***/findByCustomerId*** finds orders belongs to a customer.
* ***/getTotalCount*** counts the number of all orders.
* ***/findAll*** fetches all orders.

### 1.4 Statistics Application

This application is responsible for statistics domain.

Exposed endpoints:

* ***/monthlyCustomerOrders*** returns a list of a customer' s orders grouping by month.

## Technology Stack
* Java - version 11
* Spring Boot - version 2.6.1
* Mongodb

## Postman collection

***getir.postman_collection.json*** has been added into root path of the repository.
You can find sample rest call in this collection.