/*alter table question alter column creator bigint default not null;
alter table comment alter column commentator bigint default not null;*/
alter table question modify creator bigint not null;
alter table `comment` modify commentator bigint not null;