package br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;

@Repository
public class PatientScheduleJDBC {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private PatientJDBC patientJDBC;
    

    public int insert(PatientSchedule patientSchedule) {
        return jdbcTemplate.update("insert into patient_schedule (patient_id, day_of_Week, times_of_month, time_of_day, type_week) values (?, ?, ?, ?, ?)", 
        patientSchedule.getPatient().getId(), patientSchedule.getDayOfWeek().getDaysOfWeek(), patientSchedule.getTimesOfMonth(), patientSchedule.getTime(), patientSchedule.getTypeWeek().getTypeWeek());
    }

    public int update(PatientSchedule patientSchedule) {
        String update = "update patient_schedule set day_of_Week = ?, times_of_month = ? , time_of_day = ?, type_week = ? where patient_id = ?";               
        return jdbcTemplate.update(update, patientSchedule.getDayOfWeek().getDaysOfWeek(), patientSchedule.getTimesOfMonth(), patientSchedule.getTime(), patientSchedule.getTypeWeek().getTypeWeek(), patientSchedule.getPatient().getId());
    }

    public int deleteByPatientId(String patientId) {
        String delete = "delete from patient_schedule where patient_id = ?";               
        return jdbcTemplate.update(delete, patientId);
    }

    public PatientSchedule getScheduleByPatienteId(String patientId) {
        String query = "select * from patient_schedule where patient_id = ?";
        try {            
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createPatientSchedule(rs), new Object[]{patientId});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public PatientSchedule getScheduleByPatientePeriod(LocalTime timeIni, LocalTime timeFin, DaysOfWeekEnum dayOfWeek, PatientSchedule.TypeWeekEnum typeWeek) {
        String query = "select * from patient_schedule where time_of_day >= ? and time_of_day < ? and day_of_Week = ? and type_week = ?";
        try {            
            return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createPatientSchedule(rs), new Object[]{timeIni.toString(), timeFin.toString(), dayOfWeek.getDaysOfWeek(), typeWeek.getTypeWeek()});
        } catch (EmptyResultDataAccessException e) {
            return null;
        }

    }

    private PatientSchedule createPatientSchedule(ResultSet rs) throws SQLException {
        return new PatientSchedule(
                   patientJDBC.findPatientById(rs.getString("patient_id")) , 
                   DaysOfWeekEnum.getDaysOfWeekEnum(rs.getString("day_of_Week")),
                   rs.getInt("times_of_month"),
                   LocalTime.parse(rs.getString("time_of_day")),
                   PatientSchedule.TypeWeekEnum.getTypeWeekEnum(rs.getString("type_week")));
    }
    
}
