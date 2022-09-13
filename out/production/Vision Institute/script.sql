DROP DATABASE IF EXISTS `vision`;
CREATE DATABASE IF NOT EXISTS `vision`;

SHOW DATABASES;

USE vision;

DROP TABLE IF EXISTS `Employee detail`;
CREATE TABLE IF NOT EXISTS `Employee detail`(
    EmployeeId VARCHAR (15) PRIMARY KEY NOT NULL,
    fName VARCHAR(55) NOT NULL ,
    lName VARCHAR (55) NOT NULL ,
    address VARCHAR (150),
    nic VARCHAR (15) NOT NULL,
    email VARCHAR (55) NOT NULL ,
    contact VARCHAR (55),
    JobRole VARCHAR (25)
);
SHOW TABLES;
DESC `Employee detail`;

#===============================================================================

DROP TABLE IF EXISTS `Employee salary`;
CREATE TABLE IF NOT EXISTS `Employee salary`(
    EmployeeId VARCHAR (15),
    EmployeeName VARCHAR (55),
    jobRole VARCHAR (15) NOT NULL ,
    WorkingDays INT (5),
    SalaryPerDay Double (20,2) DEFAULT 0.00,
    allowances Double(20,4) DEFAULT 0.00,
    deductions Double(20,4) DEFAULT 0.00,
    NetSalary DECIMAL (20,2) DEFAULT 0.00,
    EMonth VARCHAR (15),
    EDate DATE,
    ETime TIME
);
SHOW TABLES;
DESC `Employee salary`;

#===============================================================================

DROP TABLE IF EXISTS `login detail`;
CREATE TABLE IF NOT EXISTS `login detail`(
    firstName VARCHAR (55),
    lastName VARCHAR (55),
    address VARCHAR (155),
    contact VARCHAR (15),
    email VARCHAR (55),
    nic VARCHAR (55),
    userName VARCHAR (55),
    password VARCHAR (55),
    userType VARCHAR (35)
);
SHOW TABLES;
DESC `login detail`;

#===============================================================================

DROP TABLE IF EXISTS Subject;
CREATE TABLE IF NOT EXISTS Subject(
    subId VARCHAR (15) PRIMARY KEY NOT NULL,
    subjectName VARCHAR (55) NOT NULL,
    subjectDescription VARCHAR (155)
);
SHOW TABLES;
DESCRIBE Subject;


#=======================================================================================================

DROP TABLE IF EXISTS Teacher;
CREATE TABLE IF NOT EXISTS Teacher(
    TeacherId VARCHAR (15) NOT NULL,
    TeacherName VARCHAR (25),
    teacherEmail VARCHAR (50) NOT NULL,
    teacherContact VARCHAR (15),
    teacherAddress VARCHAR (50),
    nic VARCHAR (15)NOT NULL,
    teacherDescription TEXT,
    subName VARCHAR (20)NOT NULL,
    fee DOUBLE (20,2)DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (TeacherId)
);
SHOW TABLES;
DESCRIBE Teacher;

#===========================================================================

DROP TABLE IF EXISTS Student;
CREATE TABLE IF NOT EXISTS Student(
    sID VARCHAR (15) NOT NULL ,
    fullName VARCHAR (55),
    address VARCHAR (15),
    nic VARCHAR (15) NOT NULL ,
    email VARCHAR (55) NOT NULL ,
    gender VARCHAR (55) ,
    contact VARCHAR (15) ,
    parentName VARCHAR (55) ,
    Examyear VARCHAR (15) ,
    RegistrationFee VARCHAR(55) NOT NULL,
    regDate VARCHAR(55),
    CONSTRAINT PRIMARY KEY (sID)
);
SHOW TABLES;
DESC Student;

#===========================================================================


DROP TABLE IF EXISTS `Student Detail`;
CREATE TABLE IF NOT EXISTS `Student Detail`(
    StudentId VARCHAR (10) NOT NULL,
    StudentName VARCHAR (25) NOT NULL,
    Examyear VARCHAR (10) NOT NULL,
    TeacherId VARCHAR (15) NOT NULL,
    TeacherName VARCHAR (25) NOT NULL,
    subName VARCHAR (25) NOT NULL
);
SHOW TABLES;
DESCRIBE `Student Detail`;

#==============================================================================

DROP TABLE IF EXISTS payment;
CREATE TABLE IF NOT EXISTS payment(
    sID VARCHAR (15) NOT NULL ,
    cardNumber VARCHAR (55),
    subject VARCHAR (55) NOT NULL ,
    teacher VARCHAR (55) NOT NULL ,
    paymentMonth VARCHAR (55),
    amount DOUBLE ,
    pDate DATE ,
    pTime TIME
);
SHOW TABLES;
DESC payment;

#INSERT INTO payment VALUES ('s-002','5544','Maths','sujith','January','500.0','2021-09-21','20:08');
#====================================================================================

DROP TABLE IF EXISTS `Student Attendance`;
CREATE TABLE IF NOT EXISTS `Student Attendance`(
    StudentId VARCHAR (10) NOT NULL,
    StudentName VARCHAR (55),
    Attendance VARCHAR (10),
    SubjectName VARCHAR (55),
    teacherName VARCHAR (55),
    examYear VARCHAR (55),
    ATime Time ,
    ADate DATE
);
SHOW TABLES;
DESCRIBE `Student Attendance`;

#=================================================================================

DROP TABLE IF EXISTS `Evaluation Result`;
CREATE TABLE IF NOT EXISTS `Evaluation Result`(
    StudentId VARCHAR (15) NOT NULL,
    StudentName VARCHAR (55),
    SubjectName VARCHAR (55),
    IssuedDate DATE,
    Result VARCHAR (10),
    examName VARCHAR (55),
    examMonth VARCHAR (55),
    marks VARCHAR (55)
);
SHOW TABLES;
DESCRIBE `Evaluation Result`;

#===============================================================================

DROP TABLE IF EXISTS Schedule;
CREATE TABLE IF NOT EXISTS Schedule(
    TeacherId VARCHAR (15),
    TeacherName VARCHAR (55),
    SubjectName VARCHAR (55),
    examYear VARCHAR (55),
    StartTime TIME,
    EndTime TIME,
    Sdate DATE,
    description VARCHAR (55),
    hallNumber VARCHAR (55)
);
SHOW TABLES;
DESCRIBE Schedule;

#===============================================================================

DROP TABLE IF EXISTS `Teacher Salary`;
CREATE TABLE IF NOT EXISTS `Teacher Salary`(
    TeacherId VARCHAR (15),
    TeacherName VARCHAR (55),
    totalFees DECIMAL (20,2),
    percentage Double(20,10) DEFAULT 0.00,
    allowances Double(20,4) DEFAULT 0.00,
    deductions Double(20,4) DEFAULT 0.00,
    NetSalary DECIMAL (20,2) DEFAULT 0.00,
    TMonth VARCHAR (15),
    TDate DATE,
    TTime TIME
);
SHOW TABLES;
DESCRIBE `Teacher Salary`;

#==============================================================================

DROP TABLE IF EXISTS OwnerDetails;
CREATE TABLE IF NOT EXISTS OwnerDetails(
    ownerName VARCHAR (55) NOT NULL,
    Contact VARCHAR (15) NOT NULL
);
SHOW TABLES;
DESCRIBE OwnerDetails;

