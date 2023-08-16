drop table if exists patient;
drop table if exists plan;
drop table if exists support;

create table plan (
    id varchar(255) not null,
    type varchar(20) not null,
    price int not null,
    primary key (id)
) engine=InnoDB;

create table patient (
	id varchar(255) not null,
    name varchar(255) not null,
    cpf varchar(14),
    rg varchar(12),
    date_birth date,
    email varchar(255),
    plan_id varchar(255),
    responsible_id varchar(255),
    observation varchar(500),
    primary key (id),
    foreign key (plan_id) references plan(id)
) engine=InnoDB;  

create table support (
    id varchar(255) not null,
    patient_id varchar(255) not null,
    date_suport datetime not null,
    observation varchar(500),
    primary key (id)   
) engine=InnoDB;

alter table patient add constraint email_constraint unique (email);
alter table patient add constraint cpf_constraint unique (cpf);
alter table patient add constraint rg_constraint unique (rg);

insert into plan (id, type, price) values ('plan_id', 'top', 500);
insert into patient (id, name, cpf, rg, email, date_birth, plan_id, responsible_id, observation) values ('paciente_1', 'Seiya de Pagasus', '879.597.845-98', '74.682.668-4', 'seiya@email.com', '1988-04-14', 'plan_id', null, 'Muito confiante');
insert into patient (id, name, cpf, rg, email, date_birth, plan_id, responsible_id, observation) values ('paciente_2', 'Saori Athena', '215.444.554-88', '11.22.654-7', 'saori@email.com', '1991-09-15', 'plan_id', null, 'Muito Bom');
insert into patient (id, name, cpf, rg, email, date_birth, plan_id, responsible_id, observation) values ('paciente_3', 'Shun de Andromeda', '824.554.987-55', '55.478.665-2', 'shun@email.com', '1989-05-04', 'plan_id','paciente_2', 'Poderosa');
