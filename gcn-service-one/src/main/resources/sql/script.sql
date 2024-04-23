CREATE DATABASE test;

CREATE TABLE test.echo(
    ID int primary key auto_increment not null,
    MESSAGE varchar(255) not null,
    VERSION int
);