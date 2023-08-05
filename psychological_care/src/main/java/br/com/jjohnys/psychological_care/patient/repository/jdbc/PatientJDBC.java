package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.repository.PatientRepository;

@Component
public class PatientJDBC implements PatientRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PlanJDBC planJDBC;

    public int insertPatient(Patient patient) {

        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, email, date_birth, plan_id, observation) values (?, ?, ?, ?, ?, ?, ?, ?)",
        UUID.randomUUID().toString(), patient.getName(), patient.getCpf(), patient.getRg(), patient.getEmail(), patient.getDateBirth(), patient.getPlan().getId(), patient.getObservation());
    }

    @Override
    public Patient findPatientByName(String name) {
        return jdbcTemplate.queryForObject("select * from patient where name = ?", (rs, rowNum) -> 
            new Patient(
                rs.getString("id"), 
                rs.getString("name"), 
                rs.getString("cpf"), 
                rs.getString("rg"), 
                dateToLocalDate(rs.getString("date_birth")), 
                rs.getString("email"), 
                planJDBC.getPlanById(rs.getString("plan_id")), 
                rs.getString("observation")
            ), new Object[]{name});
    }

    @Override
    public Patient findPatientById(String id) {
        return jdbcTemplate.queryForObject("select * from patient where id = ?", (rs, rowNum) -> 
            new Patient(
                rs.getString("id"), 
                rs.getString("name"), 
                rs.getString("cpf"), 
                rs.getString("rg"), 
                dateToLocalDate(rs.getString("date_birth")), 
                rs.getString("email"), 
                planJDBC.getPlanById(rs.getString("service_price")), 
                rs.getString("observation")
            ), new Object[]{id});
    }

    private LocalDate dateToLocalDate(String date) {


        //date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return LocalDate.parse(date);
    }
    
}
