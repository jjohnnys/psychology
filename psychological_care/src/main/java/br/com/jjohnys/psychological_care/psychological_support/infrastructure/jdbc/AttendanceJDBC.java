package br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;

@Component
public class AttendanceJDBC implements AttendanceRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    PatientJDBC patientJDBC;

    public int insertAttendance(Attendance attendance) {
        return jdbcTemplate.update("insert into attendance (id, patient_id, date_suport, observation) values (?, ?, ?, ?)", attendance.getId(), attendance.getPatient().getId(), attendance.getDateSuport(), attendance.getObservation());        
    }

    public int updateAttendance(Attendance attendance) {
        return jdbcTemplate.update("update attendance set patient_id, date_suport, observation) where id = ?", attendance.getPatient().getId(), attendance.getDateSuport(), attendance.getObservation(), attendance.getId());
    }

    public int deleteAttendance(String attendanceId) {
        return jdbcTemplate.update("delete from attendance where id = ?", attendanceId);
    }

    public Attendance getAttendanceById(String attendanceId) {        
        String query = "select * from attendance where id = '" + attendanceId + "'";
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> createAttendance(rs));
    }

    public List<Attendance> getAttendanceByDate(LocalDate date) {
        String query = "select * from attendance where DATE(date_suport) = '" + date + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createAttendance(rs));
    } 

    public List<Attendance> getAttendanceByPatient(String namePatiente) {
        String query = "select s.id, s.patient_id, s.date_suport, s.observation from attendance s, patient p where s.patient_id = p.id and p.name like '%" + namePatiente + "%'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createAttendance(rs));
    }

    public List<Attendance> getPeriodOfAttendanceByPatientAndPeriod(String patienteId, LocalDate dateStart, LocalDate dateEnd) {
        String query = "select s.id, s.patient_id, s.date_suport, s.observation from attendance s, patient p where s.patient_id = p.id and p.id = '" + patienteId + "' and DATE(date_suport) >= '" + dateStart + "' and DATE(date_suport) <= '" + dateEnd + "'";
        return jdbcTemplate.query(query, (rs, rowNum) -> createAttendance(rs));
    }

    private Attendance createAttendance(ResultSet rs) throws SQLException {
        return new Attendance(rs.getString("id"), 
            patientJDBC.findPatientById(rs.getString("patient_id")), 
            stringToLocaldateTime(rs.getString("date_suport")), 
            rs.getString("observation"));
    }

    private LocalDateTime stringToLocaldateTime(String dateTime) {
        System.out.println(dateTime.replace(" ", "T"));
        return LocalDateTime.parse(dateTime.replace(" ", "T"));
    }
    
}
