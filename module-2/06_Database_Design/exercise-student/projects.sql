DROP TABLE IF EXISTS department_employee;
DROP TABLE IF EXISTS project_employee;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS department;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS  jobTitle;


CREATE TABLE jobTitle (
 jobTitleId serial NOT NULL
, jobTitle varchar(100),

CONSTRAINT pk_JobTitleId PRIMARY KEY (jobTitleId)

);

CREATE TABLE project (
 projectId serial NOT NULL
 , projectName varchar(100) NULL
 , startdate DATE NOT NULL DEFAULT CURRENT_DATE,
 
 CONSTRAINT pk_projectId PRIMARY KEY (projectId)

);

CREATE TABLE department (
 departmentId serial NOT NULL
 , departmentName varchar(100) NOT NULL,
 
  CONSTRAINT pk_departmentId PRIMARY KEY (departmentId)

);


CREATE TABLE employee (
  employeeId serial NOT NULL
, jobTitleId int NOT NULL
, birthday DATE NOT NULL
, hireDate DATE NOT NULL DEFAULT CURRENT_DATE
, gender varchar(10) NULL
, firstName varchar(100)
, lastName varchar(100),

CONSTRAINT pk_employeeId PRIMARY KEY (employeeId),
CONSTRAINT fk_jobTitleId FOREIGN KEY (jobTitleId) REFERENCES jobTitle(jobTitleId),
CONSTRAINT chk_gender
        CHECK ((gender = 'M') 
        OR (gender = 'F')
        OR (gender IS NULL))
);


CREATE TABLE department_employee (
 departmentId serial NOT NULL
, employeeId serial NOT NULL,
 
 CONSTRAINT pk_departmentId_employeeId PRIMARY KEY (departmentId, employeeId),
 CONSTRAINT fk_departmentId FOREIGN KEY (departmentId) REFERENCES department(departmentId),
 CONSTRAINT fk_employeeId FOREIGN KEY (employeeId) REFERENCES employee(employeeId)

);


CREATE TABLE project_employee (
 projectId serial NOT NULL
 , employeeId serial NOT NULL,
 
  CONSTRAINT pk_employeeid_projectId PRIMARY KEY (employeeId, projectId),
  CONSTRAINT fk_projectId FOREIGN KEY (projectId) REFERENCES project(projectId),
  CONSTRAINT fk_employeeid FOREIGN KEY (employeeId) REFERENCES employee(employeeId)
);

INSERT INTO jobTitle(jobTitle) Values('Mega Coder');
INSERT INTO jobTitle(jobTitle) Values('Absolute Unit');
INSERT INTO jobTitle(jobTitle) Values('Monarch');
INSERT INTO jobTitle(jobTitle) Values('Employee');


INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (1, '2/11/1997', current_timestamp, 'M', 'Albert', 'Sands');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (2, '9/10/1972', current_timestamp, 'M', 'Snow', 'Days');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (2, '11/11/1976', current_timestamp, 'M', 'Larrs', 'Hjekm');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (1, '8/8/1999', current_timestamp, 'F', 'Lucy', 'Lou');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (4, '10/10/2000', current_timestamp, 'M', 'Martin', 'Martian');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (3, '2/2/2001', current_timestamp, 'M', 'Bart', 'Blakers');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (4, '6/6/1989', current_timestamp, 'F', 'Kendall', 'Kooser');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (4, '7/7/1976', current_timestamp, 'F', 'Molly', 'McCan');
INSERT INTO employee( jobTitleId, birthday, hireDate, gender, firstName, lastName) VALUES (4, '5/5/1946', current_timestamp, 'F', 'Lisa', 'Browne');

INSERT INTO project(projectName, startdate) VALUES ('Finding Lost Treasure', '7/12/2010');
INSERT INTO project(projectName, startdate) VALUES ('Building A Canoe', '4/11/2011');
INSERT INTO project(projectName, startdate) VALUES ('Going To Disney World', '8/8/2018');
INSERT INTO project(projectName, startdate) VALUES ('Parallel Parking', '4/4/2020');

INSERT INTO department(departmentName) VALUES ('The Colony');
INSERT INTO department(departmentName) VALUES ('Sun Room Boys');
INSERT INTO department(departmentName) VALUES ('Marketing');

INSERT INTO department_employee VALUES (1, 1);
INSERT INTO department_employee VALUES (1, 2);
INSERT INTO department_employee VALUES (2, 3);
INSERT INTO department_employee VALUES (2, 4);
INSERT INTO department_employee VALUES (3, 5);
INSERT INTO department_employee VALUES (3, 6);
INSERT INTO department_employee VALUES (3, 7);
INSERT INTO department_employee VALUES (2, 8);
INSERT INTO department_employee VALUES (1, 9);

INSERT INTO project_employee Values (1,1);
INSERT INTO project_employee Values (1,2);
INSERT INTO project_employee Values (1,3);
INSERT INTO project_employee Values (2,4);
INSERT INTO project_employee Values (2,5);
INSERT INTO project_employee Values (3,6);
INSERT INTO project_employee Values (4,7);
INSERT INTO project_employee Values (3,8);
INSERT INTO project_employee Values (3,9);