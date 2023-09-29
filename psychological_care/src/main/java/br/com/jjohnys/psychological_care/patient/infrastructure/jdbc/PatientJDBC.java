package br.com.jjohnys.psychological_care.patient.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.Gender;

@Repository
public class PatientJDBC{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertPatient(Patient patient) {
        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, observation) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
        genereteID(patient.getId()), patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getObservation());
    }

    public int updatePatient(Patient patient) {
        String update = "update patient set name = ?, cpf = ?, rg = ?, date_birth = ?, price = ?, schooling = ?, gender = ?, address = ?, observation = ? where id = ?";               
        return jdbcTemplate.update(update, patient.getName(), patient.getCpf(), patient.getRg(), patient.getDateBirth(), patient.getPrice(),  patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getObservation(), patient.getId());
    }

    public List<Patient> findPatientByName(String name) {        
        return jdbcTemplate.query("select * from patient where name like ?", (rs, rowNum) -> 
            createPatient(rs), new Object[]{"%"+name+"%"});
    }

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
                (rs.getInt("price")),
                rs.getString("schooling"),
                Gender.getGenderEnum(rs.getString("gender")),
                rs.getString("address"),
                rs.getString("observation")
            );
    }

    private String genereteID(String id) {
        if(id == null || id.isBlank()) return UUID.randomUUID().toString();
        return id;
    }

    
    
}
