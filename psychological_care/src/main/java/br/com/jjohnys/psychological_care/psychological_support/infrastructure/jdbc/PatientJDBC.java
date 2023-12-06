package br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.GenderEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.psychological_support.domain.value_objects.CPF;

@Repository
public class PatientJDBC{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertPatient(Patient patient) {
        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, date_birth, price, schooling, gender, address, status, observation) values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
            genereteID(patient.getId()), patient.getName(), patient.getCpf().get(), patient.getRg(), patient.getDateBirth(), patient.getPrice(), patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getStatus().getStatus(), patient.getObservation());
    }

    public int updatePatient(Patient patient) {
        String update = "update patient set name = ?, cpf = ?, rg = ?, date_birth = ?, price = ?, schooling = ?, gender = ?, address = ?, status = ?, observation = ? where id = ?";               
        return jdbcTemplate.update(update, patient.getName(), patient.getCpf().get(), patient.getRg(), patient.getDateBirth(), patient.getPrice(),  patient.getSchooling(), patient.getGender().getDescription(), patient.getAddress(), patient.getStatus().getStatus(),  patient.getObservation(), patient.getId());
    }

    public List<Patient> findPatientByName(String name) {        
        return jdbcTemplate.query("select * from patient where name like ?", (rs, rowNum) -> 
            createPatient(rs), new Object[]{"%"+name+"%"});
    }

    public Patient findPatientById(String id) {
        try {
            return jdbcTemplate.queryForObject("select * from patient where id = ?", (rs, rowNum) -> createPatient(rs), new Object[]{id});            
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public Patient findPatientByCPF(CPF cpf) {
        try {            
            return jdbcTemplate.queryForObject("select * from patient where cpf = ?", (rs, rowNum) -> createPatient(rs), new Object[]{cpf.get()});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean existisOtherPatientWithSameCPF(String id, CPF cpf) {

        StringBuilder sb = new StringBuilder();
        sb.append("select * from patient where ");
        if(id != null && !id.isBlank()) sb.append("id <> '" + id + "' and ");            
        sb.append("cpf = '" + cpf.get() + "'");
        try {            
           return jdbcTemplate.queryForObject(sb.toString(), (rs, rowNum) -> createPatient(rs)) != null;
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
    }

    public int changeStatusPatient(String patientId, PatientStatusEnum status) {
        return jdbcTemplate.update("update patient set status = ? where id = ?", status.getStatus(), patientId);
    }


    private Patient createPatient(ResultSet rs) throws SQLException {
        return new Patient(
                rs.getString("id"), 
                rs.getString("name"), 
                new CPF(rs.getString("cpf")), 
                rs.getString("rg"), 
                (LocalDate.parse(rs.getString("date_birth"))),
                (rs.getInt("price")),
                rs.getString("schooling"),
                GenderEnum.getGenderEnum(rs.getString("gender")),
                rs.getString("address"),
                PatientStatusEnum.getStatusPatientEnum(rs.getString("status")),
                rs.getString("observation")
            );
    }

    private String genereteID(String id) {
        if(id == null || id.isBlank()) return UUID.randomUUID().toString();
        return id;
    }

    
    
}
