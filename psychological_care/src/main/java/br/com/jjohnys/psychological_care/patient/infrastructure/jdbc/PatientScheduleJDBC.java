package br.com.jjohnys.psychological_care.patient.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;

@Repository
public class PatientScheduleJDBC {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PatientJDBC patientJDBC;
    

    public int insert(PatientSchedule patientSchedule) {
        return jdbcTemplate.update("insert into patient_schedule (patient_id, day_of_Week, times_of_month, time_of_day) values (?, ?, ?, ?)", 
        patientSchedule.getPatient().getId(), patientSchedule.getDayOfWeek().getDaysOfWeek(), patientSchedule.getTimesOfMonth(), patientSchedule.getTime());
    }

    public int update(PatientSchedule patientSchedule) {
        String update = "update patient_schedule set day_of_Week = ?, times_of_month = ? , time_of_day = ? where patient_id = ?";               
        return jdbcTemplate.update(update, patientSchedule.getDayOfWeek(), patientSchedule.getTimesOfMonth(), patientSchedule.getTime(), patientSchedule.getPatient().getId());
    }

    public PatientSchedule getScheduleByPatienteId(String patientId) {
        String query = "select * from patient_schedule where patient_id = ?";
        try {            
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createPatientSchedule(rs), new Object[]{patientId});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    private PatientSchedule createPatientSchedule(ResultSet rs) throws SQLException {
        return new PatientSchedule(
                   patientJDBC.findPatientById(rs.getString("patient_id")) , 
                   DaysOfWeekEnum.getDaysOfWeekEnum(rs.getString("day_of_Week")),
                   rs.getInt("times_of_month"),
                   LocalTime.parse(rs.getString("time_of_day")));
    }
    
}
