USE master;

IF DB_ID('FerrariDB') IS NOT NULL
	DROP DATABASE FerrariDB;

CREATE DATABASE FerrariDB;
GO

USE FerrariDB;

CREATE TABLE [Car] (
  [id] INT PRIMARY KEY IDENTITY (1,1), -- svarer til stelnummer
  [model] NVARCHAR(255) NOT NULL,
  [year] INT NOT NULL,
  [price] DECIMAL(10,2) NOT NULL
);

CREATE TABLE [Customer] (
  [id] INT PRIMARY KEY IDENTITY (1,1),
  [first_name] NVARCHAR(255) NOT NULL,
  [last_name] NVARCHAR(255) NOT NULL,
  [phone_number] NVARCHAR(255) NOT NULL,
  [email] NVARCHAR(255) NOT NULL,
  [address] NVARCHAR(255) NOT NULL,
  [cpr] NVARCHAR(255) NOT NULL
);

CREATE TABLE [Employee] (
  [id] INT PRIMARY KEY IDENTITY (1,1),
  [first_name] NVARCHAR(255) NOT NULL,
  [last_name] NVARCHAR(255) NOT NULL,
  [phone_number] NVARCHAR(255) NOT NULL,
  [email] NVARCHAR(255) NOT NULL,
  [password] NVARCHAR(255) NOT NULL,
  [max_loan] DECIMAL(10,2) NOT NULL
);

CREATE TABLE [Loan] (
  [id] INT PRIMARY KEY IDENTITY (1,1),
  [car_id] INT NOT NULL,
  [customer_id] INT NOT NULL,
  [employee_id] INT NOT NULL,
  [loan_size] DECIMAL(10,2) NOT NULL,
  [down_payment] DECIMAL(10,2) NOT NULL,
  [interest_rate] DECIMAL(5,2) NOT NULL,
  [start_date] DATE NOT NULL, -- sættes tilpas ude i fremtiden så det kan godkendes
  [end_date] DATE NOT NULL,
  [status] INT NOT NULL,
  FOREIGN KEY ([car_id]) REFERENCES [Car]([id]),
  FOREIGN KEY ([customer_id]) REFERENCES [Customer]([id]),
  FOREIGN KEY ([employee_id]) REFERENCES [Employee]([id])
);

INSERT INTO [Employee] VALUES ('Sales', 'Manager', '88888888', 's@m.dk', '123', -1);
INSERT INTO [Employee] VALUES ('Sales', 'Woman', '13371337', 's@w.dk', '123', 1000000);