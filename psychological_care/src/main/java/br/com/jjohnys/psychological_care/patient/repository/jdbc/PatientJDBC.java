package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;
import br.com.jjohnys.psychological_care.patient.repository.PatientRepository;

@Component
public class PatientJDBC implements PatientRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PlanJDBC planJDBC;

    public int insertPatient(Patient patient) {
        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, date_birth, plan_id, schooling, gender, address, responsible_id, observation) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        patient.getId(), patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPlan().getId(), patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), responsibleId(patient.getResponsible()), patient.getObservation());
    }

    public int updatePatient(Patient patient) {
        String update = "update patient set name = ?, cpf = ?, rg = ?, date_birth = ?, plan_id = ?, schooling = ?, gender = ?, address = ?, responsible_id = ?, observation = ? where id = ?";               
        return jdbcTemplate.update(update, patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPlan().getId(),  patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), responsibleId(patient.getResponsible()), patient.getObservation(), patient.getId());
    }

    @Override
    public List<Patient> findPatientByName(String name) {        
        return jdbcTemplate.query("select * from patient where name like ?", (rs, rowNum) -> 
            createPatient(rs), new Object[]{"%"+name+"%"});
    }

    @Override
    public Patient findPatientById(String id) {
        return jdbcTemplate.queryForObject("select * from patient where id = ?", (rs, rowNum) -> 
            createPatient(rs), new Object[]{id});
    }

    private Patient createPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("id"), 
                rs.getString("name"), 
                rs.getString("cpf"), 
                rs.getString("rg"), 
                (LocalDate.parse(rs.getString("date_birth"))),
                planJDBC.getPlanById(rs.getString("plan_id")),
                rs.getString("schooling"),
                Gender.getGenderEnum(rs.getString("gender")),
                rs.getString("address"),
                rs.getString("responsible_id") != null ? findPatientById(rs.getString("responsible_id")) : null,
                rs.getString("observation")
            );
    }

    private String responsibleId(Patient patient) {
        if(patient == null)
            return null;
        return patient.getId();
    }

    
    
}
