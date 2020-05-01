drop table if exists USERS;
create table if not exists USERS (
	userid SERIAL,
	email varchar(255) not null unique,
	username varchar(255) not null,
	password varchar(255) not null,
	PRIMARY KEY (userid)
);
