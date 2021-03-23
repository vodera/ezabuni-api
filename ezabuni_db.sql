-- drop database ezabuni;
-- drop user ezabuni;
-- create user ezabuni with password 'admin';
-- create database ezabuni is_template true owner=ezabuni;
\connect ezabuni;
alter default privileges grant all on tables to ezabuni;
alter default privileges grant all on sequences to ezabuni;

create table users(
userid integer primary key not null,
firstname varchar(20) not null,
middlename varchar(20) not null,
lastname varchar(20) not null,
email varchar(30) not null,
password varchar(50) not null,
phonenumber varchar(20),
county varchar(30) not null,
dob varchar(20) not null,
username varchar(20) not null,
departmentid integer
);

create table departments (
departmentid integer primary key not null,
department_name varchar (50) not null,
department_description varchar(255),
userid integer
);

create table tender_request (
tender_requestid integer primary key not null,
departmentid integer not null,
tender_requestname varchar(20) not null,
tender_requestbrief varchar(50) not null,
tender_requestdocument varchar(20),
entity_name varchar(30) not null,
processing_stage varchar (30) not null,
approval_status varchar(20) not null
);

alter table tender_request add column userid integer not null;

alter table tender_request add constraint request_user_fk
foreign key (userid) references users(userid);

alter table tender_request add constraint request_department_fk
foreign key (departmentid) references departments(departmentid);

alter table users add constraint users_department_fk
foreign key (departmentid) references departments(departmentid);

alter table departments add constraint users_id_fk
    foreign key (userid) references users(userid);

create sequence users_seq increment 1 start 1;
create sequence tender_request_seq increment 1 start 1;
create sequence departments_seq increment 1 start 1;

