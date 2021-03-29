BEGIN TRANSACTION;

--CREATE DATABASE database_name

DROP DATABASE IF EXISTS artgallery;
CREATE DATABASE artgallery;

--Create tables
/*
CREATE TABLE table_name
(
column_name1 data_type(size),
column_name2 data_type(size) NOT NULL,
CONSTRAINT pk_column1 PRIMARY KEY (column_name1),
CONSTRAINT fk_column2 FOREIGN KEY (column_name2) REFERENCES table_name2(ColumnD)

);
*/

--Customers table that has a customer id, name, address and phone. customer id is the pk
CREATE TABLE customers
(
customerID SERIAL,
name varchar(64) NOT NULL,
address varchar(100) NOT NULL,
phone varchar(11) NULL,

CONSTRAINT pk_customers PRIMARY KEY(customerID)

);

-- TO DROP A TABLE
-- DROP TABLE table_name
-- DROP TABLE IF EXISTS table_name
DROP TABLE IF EXISTS something;

-- Create a table to hold the art with columns artist - this has artistid, firstname and lastname, and is is the pk.
-- write the code, take a break, and come back at 11:30
CREATE TABLE artists
(
artist_id varchar(64) NOT NULL, --NOT NULL is a bit redundant since we made it the pk, but no problem to be explicit
first_name varchar(64) NOT NULL,
last_name varchar(64) NOT NULL,
CONSTRAINT pk_artists_id PRIMARY KEY (artist_id)
);

--create a table for the art that has the artcodeid, a title, and the artist.
CREATE TABLE art
(
art_code_id SERIAL,
title varchar(64) NOT NULL,
artistid int NOT NULL, --if the artist_id is serial, my fk to it needs to be int. If the artist_id was a varchar, then this should be varchar.

CONSTRAINT pk_art PRIMARY KEY (art_code_id),
CONSTRAINT fk_art_who_did_it_ref_artists_artist_id FOREIGN KEY (who_did_it) REFERENCES artists(artist_id)
);