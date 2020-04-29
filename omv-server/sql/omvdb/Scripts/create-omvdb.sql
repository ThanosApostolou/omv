create database omvdb;
create user omv password 'omv';
grant all on database omvdb TO omv;
grant all on schema public TO omv;