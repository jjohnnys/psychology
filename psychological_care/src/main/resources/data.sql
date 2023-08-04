drop table if exists patient;

drop table if exists plan;

create table plan (
    id varchar(255),
    type varchar(20),
    price int,
    primary key (id)
) engine=InnoDB;

create table patient (
	id varchar(255) not null,
    name varchar(255),
    cpf varchar(14),
    rg varchar(12),
    date_birth date,
    email varchar(255),
    plan_id varchar,
    observation varchar(500),
    primary key (id),
    foreign key (plan_id) references by plan (id)
) engine=InnoDB;    

alter table patient add constraint email_constraint unique (email);
alter table patient add constraint cpf_constraint unique (cpf);
alter table patient add constraint rg_constraint unique (rg);

insert into plan (id, type, price) values ('plan_id', 'top', 500);
insert into patient (id, name, cpf, rg, email, date_birth, plan_id, observation) values ('paciente_1', 'Seiya', '879.597.845-98', '74.682.668-4', 'seiya@email.com', '1988-04-14', 'plan_id', 'Muito confiante');
