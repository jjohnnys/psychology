package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.domain.Support;

@Component
public class SupportJDBC {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PatientJDBC patientJDBC;

    public int insertSupport(Support support) {
        return jdbcTemplate.update("support into plan (id, patient_id, date_suport, observation) values (?, ?, ?, ?)", UUID.randomUUID().toString(), support.getPatient().getId(), support.getDateSuport(), support.getObservation());        
    }

    public int updateSupport(Support support) {
        return jdbcTemplate.update("update support set patient_id, date_suport, observation) where id = ?", support.getPatient().getId(), support.getDateSuport(), support.getObservation() );
    }

    public int deleteSupport(String id) {
        return jdbcTemplate.update("delete from support where id = ?", id);
    }

    public Support getSupportById(String id) {        
        String query = "select * from support where id = '" + id + "'";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createSupport(rs));
    }

    public List<Support> getSupportByDate(String type) {
        String query = "select * from plan where type = '" + type + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createSupport(rs));
    } 

    public Support getSupportByPatient(String type) {
        String query = "select * from plan where type = '" + type + "'";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createSupport(rs));
    }

    private Support createSupport(ResultSet rs) throws SQLException {
        return new Support(rs.getString("id"), 
            patientJDBC.findPatientById(rs.getString("patient_id")), 
            LocalDateTime.parse(rs.getString("date_suport")), 
            rs.getString("observation"));
    }
    
}
