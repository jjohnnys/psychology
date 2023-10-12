drop table if exists contact;
drop table if exists responsible;
drop table if exists patient;
drop table if exists support;

create table patient (
	id varchar(255) not null,
    name varchar(150) not null,
    cpf varchar(14),
    rg varchar(12),
    date_birth date,
    price int,    
    schooling varchar(100), 
    gender varchar(50), 
    address varchar (100),
    observation varchar(500),
    primary key (id)
) engine=InnoDB;

create table responsible (
    id varchar(255) not null,
    name varchar(150) not null,
    cpf varchar(14) not null,
    rg varchar(12),
    date_birth date,
    parentenge varchar(40),
    patient_id varchar(255) not null,
    primary key (id),
    foreign key (patient_id) references patient(id)
) engine=InnoDB;

create table contact(
    id varchar(255) not null,
    email varchar(150),
    telephone varchar(13) not null,
    patient_id varchar(255),
    responsible_id varchar(255),
    primary key (id),
    foreign key (patient_id) references patient (id),
    foreign key (responsible_id) references responsible (id)
) engine=InnoDB;

create table support (
    id varchar(255) not null,
    patient_id varchar(255) not null,
    date_suport datetime not null,
    observation varchar(500),
    primary key (id)   
) engine=InnoDB;

alter table patient add constraint cpf_constraint unique (cpf);
alter table patient add constraint rg_constraint unique (rg);
alter table responsible add constraint cpf_constraint_responsible unique (cpf);
alter table responsible add constraint rg_constraint_responsible unique (rg);

insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, observation) values ('paciente_1', 'Seiya de Pagasus', '879.597.845-98', '74.682.668-4', '1988-04-14', 100, 'Superior completo', 'Masculino', 'Grecia', 'Muito confiante');
insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, observation) values ('paciente_2', 'Saori Athena', '215.444.554-88', '11.22.654-7', '1991-09-15', 100, 'Dontorado', 'Feminino', 'Grecia', 'Poderosa');
insert into responsible (id, name, cpf, rg, date_birth, parentenge, patient_id) values ('paciente_3', 'Shun de Andromeda', '824.554.987-55', '55.478.665-2', '1989-05-04', "Brother", 'paciente_1');

insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_1", 'seiya@email.com', '85 985475', 'paciente_1', null); 
insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_2", 'saori@email.com', '85 254455', 'paciente_2', null);
insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_4", 'saori@email.com', '85 254455', null, 'paciente_3');
