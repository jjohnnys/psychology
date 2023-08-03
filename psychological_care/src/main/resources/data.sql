drop table atendimentopscdb.patient;
create table atendimentopscdb.patient (
	id varchar(255) not null,
    name varchar(255),
    cpf varchar(14),
    rg varchar(12),
    date_birth date,
    email varchar(255),
    observation varchar(500),
    primary key (id)
) engine=InnoDB;

alter table atendimentopscdb.patient add constraint email_constraint unique (email);
alter table atendimentopscdb.patient add constraint cpf_constraint unique (cpf);
alter table atendimentopscdb.patient add constraint rg_constraint unique (rg);


insert into patient (id, name, cpf, rg, email, date_birth, observation) values ('paciente_1', 'Seiya', '879.597.845-98', '74.682.668-4', 'seiya@email.com', '1988-04-14', 'Muito confiante');
