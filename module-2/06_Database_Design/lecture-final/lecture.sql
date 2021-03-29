
--CREATE DATABASE database_name

--DROP DATABASE IF EXISTS artgallery; Can't run this while connected to artgallery connection
--CREATE DATABASE artgallery;

BEGIN TRANSACTION;

--I moved these to the top because I'm dropping them in a DIFFERENT ORDER than i'm creating them so that i don't violate fk constraints
DROP TABLE IF EXISTS customer_purchases;
DROP TABLE IF EXISTS customers; 
DROP TABLE IF EXISTS art;
DROP TABLE IF EXISTS artists;

--Create tables
/* 
CREATE TABLE table_name
(
   column_name1 data_type(size),
   column_name2 data_type(size) NOT NULL,
   CONSTRAINT pk_tablename_ column1 PRIMARY KEY (column_name1),
   CONSTRAINT fk_column2 FOREIGN KEY (column_name2) REFERENCES table_name2(columnD)

); 
*/
--customers table that has a customer id, name address and phone. customer id is the pk

CREATE TABLE customers
(
  customerID SERIAL,
  name varchar(64) NOT NULL,
  address varchar(100) NOT NULL,
  phone varchar(11) NULL,
  
  CONSTRAINT pk_customers_customerID PRIMARY KEY(customerID)

);

-- TO DROP A TABLE
--DROP TABLE table_name
-- DROP TABLE IF EXISTS table_name
DROP TABLE IF EXISTS something;

--show violating the constraint
/*
INSERT INTO customers(name,address,phone)
VALUES('Katie','123','111');

SELECT * FROM customers;


INSERT INTO customers(name,address,phone,customerid)
VALUES('Jon','123','111',1);
*/

--create a table to hold the artists - this has artistid, first name and lastname. and id is the pk
--write the code take a break, come back at 11:30
--DROP TABLE art;
--DROP TABLE artists;



CREATE TABLE artists
(
  --artist_id varchar(64) NOT NULL, --NOT NULL is a bit redundant since we made it the pk but no problem to be explicit
  --design decision - is the artist id a natural key like ssn, then i'd use this above
  --if the artist_id is a surrogate key, like 123 then i'd make it a serial
  artist_id serial,
  first_name varchar(64) NOT NULL,
  last_name varchar(64) NULL,
  CONSTRAINT pk_artists_id PRIMARY KEY (artist_id)
);


--create a table for the art that has the artcodeid, a title, and the artist
CREATE TABLE art
(
   art_code_id SERIAL,
   title varchar(64) NOT NULL,
   who_did_it int NOT NULL,--if the artist_id is serial, my fk to it needs to be int. if the artist_id was a varchar, then this should be varchar. 
   
   CONSTRAINT pk_art PRIMARY KEY (art_code_id),
   CONSTRAINT fk_art_who_did_it_ref_artists_artist_id FOREIGN KEY (who_did_it) REFERENCES artists(artist_id)
);

--ALTER TABLE
/*
ALTER TABLE table_name
ADD CONSTRAINT pk_constraint_name PRIMARY KEY (column); 

ALTER TABLE table_name
ADD CONSTRAINT fk_constraint_name FOREIGN KEY (columnb) REFERENCES other_table_name(column_in_other_table); 

ALTER TABLE table_name
ADD CONSTRAINT check_constraint_name CHECK (column = 'value' OR column_name IN (values));

ALTER TABLE table_name
ADD CONSTRAINT check_continents CHECK (continent IN ('North America','South America','Africa'));

ALTER TABLE table_name 
RENAME COLUMN column_name TO new_column_name;
*/

 
CREATE TABLE customer_purchases
(
	customerId int not null,
	artCodeId int not null,
	purchaseDate timestamp not null,
	price money not null,

	constraint pk_customer_purchases primary key (customerId, artCodeId, purchaseDate),
	constraint fk_customer_purchases_customer foreign key (customerId) references customers (customerId),
	constraint fk_customer_purchases_art foreign key (artCodeId) references art(art_code_id)
);


COMMIT TRANSACTION;