package br.com.jjohnys.psychological_care.psychological_support.gateways;

import java.time.LocalDate;
import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;

public interface AttendanceRepository {

    int insertAttendance(Attendance attendance);
    int updateAttendance(Attendance attendance);
    int deleteAttendance(String attendanceId);
    Attendance getAttendanceById(String attendanceId);
    public List<Attendance> getAttendanceByDate(LocalDate dateSuport);
    public List<Attendance> getAttendanceByPatient(String namePatiente);
    List<Attendance> getPeriodOfAttendanceByPatientAndPeriod(String patienteId, LocalDate dateStart, LocalDate dateEnd);

    
}
