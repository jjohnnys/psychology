package br.com.jjohnys.psychological_care.psychological_support.application.usecases.AttendanceInterector;

import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;

public class UpdateAttendanceInterector extends AttendanceInterector {

    @Override
    public void save(Attendance attendance, AttendanceRepository attendanceRepository) {
        attendanceRepository.updateAttendance(attendance);
    }
    
}
