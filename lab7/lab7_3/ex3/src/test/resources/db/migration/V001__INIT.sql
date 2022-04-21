CREATE TABLE  thing_table (
    entityId serial primary key ,
    thing varchar(50),
    other varchar(50)
    );

insert into thing_table (thing,other) values ('something','other thing');
insert into thing_table (thing,other) values ('entity?','being');
insert into thing_table (thing,other) values ('behold','a thing');
