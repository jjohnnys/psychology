package br.com.jjohnys.psychological_care.psychological_support.gateways.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;
import br.com.jjohnys.psychological_care.psychological_support.infrastructure.jdbc.AttendanceJDBC;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class AttendanceRepositoryImpl implements AttendanceRepository {

    private AttendanceJDBC attendanceJDBC;

    @Override
    public int insertAttendance(Attendance attendance) {
        return attendanceJDBC.insertAttendance(attendance);
    }

    @Override
    public int updateAttendance(Attendance attendance) {
        return attendanceJDBC.updateAttendance(attendance);
    }

    @Override
    public int deleteAttendance(String attendanceId) {
        return attendanceJDBC.deleteAttendance(attendanceId);
    }

    @Override
    public Attendance getAttendanceById(String attendanceId) {
        return attendanceJDBC.getAttendanceById(attendanceId);
    }

    @Override
    public List<Attendance> getAttendanceByDate(LocalDate dateSuport) {
        return attendanceJDBC.getAttendanceByDate(dateSuport);
    }

    public Attendance getAttendanceByDateTime(LocalDateTime dateTimeSuport) {
        return attendanceJDBC.getAttendanceByDateTime(dateTimeSuport);
    }

    @Override
    public List<Attendance> getAttendanceByPatient(String namePatiente) {
        return getAttendanceByPatient(namePatiente);
    }

    @Override
    public List<Attendance> getPeriodOfAttendanceByPatientAndPeriod(String patienteId, LocalDate dateStart, LocalDate dateEnd) {
        return getPeriodOfAttendanceByPatientAndPeriod(patienteId, dateStart, dateEnd);
    }
    
    
}
