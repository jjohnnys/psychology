drop table if exists contact;
drop table if exists responsible;
drop table if exists patient_schedule;
drop table if exists payment;
drop table if exists patient;
drop table if exists attendance;

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
    status varchar (50),
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

create table attendance (
    id varchar(255) not null,
    patient_id varchar(255) not null,
    date_suport datetime not null,
    observation varchar(500),
    primary key (id)   
) engine=InnoDB;

create table patient_schedule (
    patient_id varchar(255) not null,
    day_of_Week varchar(15) not null,
    times_of_month float not null,
    time_of_day time not null,
    type_week varchar(5),
    foreign key (patient_id) references patient(id)
) engine=InnoDB;

create table payment (
    id varchar(255) not null,
    patient_id varchar(255) not null,
    payment_year int not null,
    payment_month int not null,
    payment_date date not null,
    payment_value decimal(15,2),
    primary key (id),
    foreign key  (patient_id) references patient(id)
) engine=InnoDB;

alter table patient add constraint cpf_constraint unique (cpf);
alter table responsible add constraint cpf_constraint_responsible unique (cpf);

insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values ('paciente_1', 'Seiya de Pagasus', '014.565.740-00', '74.682.668-4', '1988-04-14', 100, 'Superior completo', 'Masculino', 'Grecia', 'Em tratamento', 'Muito confiante');
insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values ('paciente_2', 'Saori Athena', '399.743.590-15', '11.22.654-7', '1991-09-15', 100, 'Dontorado', 'Feminino', 'Grecia', 'Tratamento Finalizado', 'Forte');
insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values ('paciente_4', 'Shiriu', '877.441.558-88', '22.75.854-4', '1990-08-10', 100, 'Dontorado', 'Feminino', 'Grecia', 'Tratamento Finalizado', 'Forte');
insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values ('paciente_5', 'June', '581.743.210-20', '55.75.854-4', '1991-09-15', 100, 'Dontorado', 'Feminino', 'Grecia', 'Tratamento Finalizado', 'Persistente');
insert into responsible (id, name, cpf, rg, date_birth, parentenge, patient_id) values ('paciente_3', 'Shun de Andromeda', '050.495.530-67', '55.478.665-2', '1989-05-04', "Brother", 'paciente_1');

insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_1", 'seiya@email.com', '11 85488-5844', 'paciente_1', null); 
insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_2", 'saori@email.com', '11 81128-5224', 'paciente_2', null);
insert into contact (id, email, telephone, patient_id, responsible_id) values ("contact_4", 'saori@email.com', '85 85258-5884', null, 'paciente_3');

insert into patient_schedule (patient_id, day_of_Week, times_of_month, time_of_day, type_week) values ('paciente_2', 'Terca-feira', 2, '10:30:00', 'par');
insert into patient_schedule (patient_id, day_of_Week, times_of_month, time_of_day, type_week) values ('paciente_5', 'Sexta-feira', 4, '10:30:00', 'todas');

insert into attendance (id, patient_id, date_suport, observation) values ('attendance_1', 'paciente_2', '2023-11-04 20:00:00', 'Requisitos atendidos');