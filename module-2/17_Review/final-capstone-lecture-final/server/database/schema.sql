BEGIN TRANSACTION;

DROP TABLE IF EXISTS users;
DROP SEQUENCE IF EXISTS seq_user_id;

CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  NO MAXVALUE
  NO MINVALUE
  CACHE 1;


CREATE TABLE users (
	user_id int DEFAULT nextval('seq_user_id'::regclass) NOT NULL,
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	role varchar(50) NOT NULL,
	CONSTRAINT PK_user PRIMARY KEY (user_id)
);

INSERT INTO users (username,password_hash,role) VALUES ('user','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_USER');
INSERT INTO users (username,password_hash,role) VALUES ('admin','$2a$08$UkVvwpULis18S19S5pZFn.YHPZt3oaqHZnDwqbCW9pft6uFtkXKDC','ROLE_ADMIN');

CREATE TABLE questions (
 question_id SERIAL,
 title varchar(100),
 question varchar(500)
 );
 
INSERT INTO questions(title,question)
VALUES('Exercise 1: SwapEnds','Create an integer array method called SwapEnds that takes in an integer array "nums". Given an array of ints, swap the first and last elements in the array. Return the modified array. The array length will be at least 1.
For example:
swapEnds([1, 2, 3, 4]) → [4, 2, 3, 1]
swapEnds([1, 2, 3]) → [3, 2, 1]
swapEnds([8, 6, 7, 9, 5]) → [5, 6, 7, 9, 8]'),

('
Exercise 3: GreenTicket','
Create an integer method called GreenTicket that takes in three integers, "a". "b", and "c"
You have a green lottery ticket, with ints a, b, and c on it. If the numbers are all different from each other,  the result is 0. If all of the numbers are the same, the result is 20. If two of the numbers are the same, the  result is 10.
greenTicket(1, 2, 3) → 0
greenTicket(2, 2, 2) → 20
greenTicket(1, 1, 2) → 10
'),
('
Exercise 4: Start1','
Create an integer method called Start1 that takes in two integer arrays "a" and "b". Start with 2 int arrays, a and b, of any length. Return how many of the arrays have 1 as their first element.
start1([1, 2, 3], [1, 3]) → 2
start1([7, 2, 3], [1]) → 1
start1([1, 2], []) → 1
'),
('
Exercise 5: FizzArray3','
Create an integer array method called FizzArray3  that takes in two integers "start" and "end"
Given start and end numbers, return a new array containing the sequence of integers from start up to but  not including end, so start=5 and end=10 yields {5, 6, 7, 8, 9}. The end number will be greater or equal  to the start number. Note that a length-0 array is valid.
fizzArray3(5, 10) → [5, 6, 7, 8, 9]
fizzArray3(11, 18) → [11, 12, 13, 14, 15, 16, 17]
fizzArray3(1, 3) → [1, 2]
'),
('
Exercise 6: Only14','
Create a boolean method called Only14 that takes in an integer array “nums”. Given an array of ints, return true if every element is a 1 or a 4.
only14([1, 4, 1, 4]) → true
only14([1, 4, 2, 4]) → false
only14([1, 1]) → true
'),
('
Exercise 7: NoTriples','
Create a boolean method called NoTuples that takes in an integer array “nums”.  Given an array of ints, we''ll say that a triple is a value appearing 3 times in a row in the array. Return true if the array does not contain any triples.
noTriples([1, 1, 2, 2, 1]) → true
noTriples([1, 1, 2, 2, 2, 1]) → false
noTriples([1, 1, 1, 2, 2, 2, 1]) → false
'),
('
Exercise 8: No23','
Create a method of type boolean called No23 that takes in an integer array “nums”. Given an int array length 2, return true if it does not contain a 2 or 3.
no23([4, 5]) → true
no23([4, 2]) → false
no23([3, 5]) → false
'),
('
Exercise 9: No14','
Create a boolean method called No14 that takes in an integer array “nums”. Given an array of ints, return true if it contains no 1''s and it contains no 4''s.
no14([7, 2, 3]) → true
no14([1, 2, 3, 4]) → false
no14([2, 3, 4]) → false
'),
('
Exercise 10: More14','
Create a boolean method called More14 that takes in an array of type integer “nums”. Given an array of ints, return true if the number of 1''s is greater than the number of 4''s
more14([1, 4, 1]) → true
more14([1, 4, 1, 4]) → false
more14([1, 1]) → true
');



COMMIT TRANSACTION;
