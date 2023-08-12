package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Support;
import br.com.jjohnys.psychological_care.patient.repository.SupportRepository;

@Component
public class SupportJDBC implements SupportRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PatientJDBC patientJDBC;

    public int insertSupport(Support support) {
        return jdbcTemplate.update("insert into support (id, patient_id, date_suport, observation) values (?, ?, ?, ?)", UUID.randomUUID().toString(), support.getPatient().getId(), support.getDateSuport(), support.getObservation());        
    }

    public int updateSupport(Support support) {
        return jdbcTemplate.update("update support set patient_id, date_suport, observation) where id = ?", support.getPatient().getId(), support.getDateSuport(), support.getObservation(), support.getId());
    }

    public int deleteSupport(String supportId) {
        return jdbcTemplate.update("delete from support where id = ?", supportId);
    }

    public Support getSupportById(String supportId) {        
        String query = "select * from support where id = '" + supportId + "'";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createSupport(rs));
    }

    public List<Support> getSupportByDate(LocalDate dateSuport) {
        String query = "select * from support where DATE(date_suport) = '" + dateSuport + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createSupport(rs));
    } 

    public List<Support> getSupportByPatient(String namePatiente) {
        String query = "select s.id, s.patient_id, s.date_suport, s.observation from support s, patient p where s.patient_id = p.id and p.name like '%" + namePatiente + "%'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createSupport(rs));
    }

    private Support createSupport(ResultSet rs) throws SQLException {
        return new Support(rs.getString("id"), 
            patientJDBC.findPatientById(rs.getString("patient_id")), 
            stringToLocaldateTime(rs.getString("date_suport")), 
            rs.getString("observation"));
    }

    private LocalDateTime stringToLocaldateTime(String dateTime) {
        System.out.println(dateTime.replace(" ", "T"));
        return LocalDateTime.parse(dateTime.replace(" ", "T"));
    }
    
}
