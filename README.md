# Orbit — Subscription Management System

A Spring Boot + Thymeleaf + MySQL starter application for the Odoo x Lucknow subscription-management challenge.

## Requirements
- Java 21
- Maven 3.9+
- MySQL Community Server 8+
- IntelliJ IDEA or VS Code

## 1. Check MySQL on macOS
Open Terminal and run:
```bash
mysql --version
```
Then try:
```bash
mysql -u root -p
```
If the second command opens the MySQL prompt, the server is running and you know the root password. If `mysql` is not found, check that **MySQL Server** (not only MySQL Workbench) is installed and running. Workbench is a client; it does not itself run the database server.

## 2. Create the database
In MySQL Workbench, connect to your local server and run:
```sql
CREATE DATABASE subscription_db;
```

## 3. Configure database credentials
Open `src/main/resources/application.properties`.
Set the username/password, or set environment variables:
```bash
export DB_USERNAME=root
export DB_PASSWORD='your_mysql_password'
```
The default database URL creates the database if it does not exist, but the MySQL server must already be running.

## 4. Run
From the project root:
```bash
mvn spring-boot:run
```
Open http://localhost:8080

## Demo flow
1. Add a customer.
2. Add a plan.
3. Create a subscription (starts as DRAFT).
4. Move it through QUOTATION → CONFIRMED → ACTIVE.
5. Return to Overview and generate an invoice.
6. Open Invoices & payments and record a payment.
7. Return to Overview to see live database totals update.

## Team Git workflow
Each member should clone the shared repository and create a feature branch:
```bash
git checkout -b feature/customer-module
```
Commit work regularly:
```bash
git add .
git commit -m "Add customer management"
git push -u origin feature/customer-module
```
Open a Pull Request on GitHub. Do not all work directly on `main`.

## Important current scope
This is a working MVP foundation, not yet the complete competition specification. It currently includes customer/product/plan/subscription/invoice/payment records and a dynamic dashboard. Before submission, add and test authentication/role permissions, quotation templates, tax and discount rules, invoice sending/printing, scheduled recurring billing/renewals, audit history, and stronger automated tests. Never expose this demo database publicly with default credentials.
