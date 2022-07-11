# atm-app

## Features Implemented
* Implement REST API
* Complete database interaction with the domain model
* Initialize the ATM (Including ATM balance)
* Check the account balance as well as withdraw functionality
* Details for dispence money

## Technologies Used
* Java
* SpringBoot
* Maven
* MySQL
* Junit5/Mockito
* Docker

How to Run the application
* By using Dockerfile


* By using docker compose


After run the application please execute the following SQL queries before accessing the APIs. Which will create both accounts in the database.
~~~sql
insert into zinkworks_atm.accounts (id, account_number, balance, overdraft_amount, pin) values (1,123456789, 800.00, 200.00, 1234);
~~~
~~~sql
insert into zinkworks_atm.accounts (id, account_number, balance, overdraft_amount, pin) values (2,987654321, 1230.00, 150.00, 4321);
~~~