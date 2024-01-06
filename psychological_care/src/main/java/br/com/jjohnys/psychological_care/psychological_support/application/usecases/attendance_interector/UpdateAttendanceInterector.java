package br.com.jjohnys.psychological_care.psychological_support.application.usecases.attendance_interector;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;

@Service
public class UpdateAttendanceInterector extends AttendanceInterector {

    @Override
    public void save(Attendance attendance) {
        attendanceRepository.updateAttendance(attendance);
    }
    
}
