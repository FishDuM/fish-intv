create database fish_intv;

create extension vector;

create table fish_user (
    id bigint primary key,
    username varchar(30) not null,
    password varchar(128) not null,
    is_admin int not null default '0',
    is_vip int not null default '0'
)

