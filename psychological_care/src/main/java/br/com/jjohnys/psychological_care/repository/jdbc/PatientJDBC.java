package br.com.jjohnys.psychological_care.repository.jdbc;

import java.sql.Date;
import java.time.LocalDate;
import java.time.ZoneId;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.domain.Patient;
import br.com.jjohnys.psychological_care.repository.PatientRepository;

@Component
public class PatientJDBC implements PatientRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertPatient(String id, String name, String cpf, String rg, String email, String date_birth, Integer priceService, String  observation) {

        return jdbcTemplate.update("insert into patient (id, name, cpf, rg, email, date_birth, service_price, observation) values (?, ?, ?, ?, ?, ?, ?, ?)",
        id, name, cpf, rg, email, date_birth, priceService, observation);
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
                rs.getInt("service_price"), 
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
                rs.getInt("service_price"), 
                rs.getString("observation")
            ), new Object[]{id});
    }

    private LocalDate dateToLocalDate(String date) {


        //date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return LocalDate.parse(date);
    }
    
}
