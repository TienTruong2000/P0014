CREATE DATABASE P0014;
GO
USE P0014;
GO
CREATE TABLE tbl_Role(
	ID int primary key,
	name nvarchar(50) not null,
	description nvarchar(500)
)
GO
CREATE TABLE tbl_User(
	gmail varchar(320) primary key,
	passsword char(64),
	fullname nvarchar(150) not null,
	status int not null,
	roleID int foreign key references tbl_Role(ID) not null
)
GO
CREATE TABLE tbl_Subject(
	ID char(6) primary key,
	name nvarchar(50) not null,
	description nvarchar(500)
)
GO
CREATE TABLE tbl_Question(
	ID char(36) primary key,
	content nvarchar(1000) not null,
	createDate bigint not null,
	status int not null,
	subjectID char(6) foreign key references tbl_Subject(ID) not null
)
GO
CREATE TABLE tbl_Choice(
	ID char(36) primary key,
	content nvarchar(1000) not null,
	status int not null,
	isCorrect bit not null,
	questionID char(36) foreign key references tbl_Question(ID) not null,
)



CREATE TABLE tbl_Test(
	ID char(36) primary key,
	name varchar(150) not null,		
	createDate bigint not null,
	openTime bigint not null,
	deadlineTime bigint not null,
	testTimeLength int not null,
	numOfQuestion int not null CHECK(numOfQuestion > 0),
	CHECK (openTime + testTimeLength*60 <= deadlineTime),
	subjectID char(6) foreign key references tbl_Subject(ID) not null
)

CREATE TABLE tbl_Quiz(
	ID char(36) primary key,
	beginTime bigint not null,
	endTime bigint not null,
	CHECK (endTime > beginTime),
	testID char(36) foreign key references tbl_Test(ID) not null,
	studentEmail varchar(320) foreign key references tbl_User(email) not null,
	mark float CHECK(mark >= 0 AND mark <= 10),
	isSubmitted bit defaul 0,
)

CREATE TABLE tbl_QuizQuestion(
	ID char(36) primary key,
	content nvarchar(1000) not null,
	quizID char(36) foreign key references tbl_Quiz(ID) not null
)

CREATE TABLE tbl_QuizChoice(
	ID char(36) primary key,
	content nvarchar(1000) not null,
	isCorrect bit not null,
	isSelect bit default 0,
	quizQuestionID char(36) foreign key references tbl_QuizQuestion(ID) not null
)

