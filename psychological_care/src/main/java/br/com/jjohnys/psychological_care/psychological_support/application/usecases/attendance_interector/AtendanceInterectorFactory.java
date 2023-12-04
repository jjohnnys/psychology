package br.com.jjohnys.psychological_care.psychological_support.application.usecases.attendance_interector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.psychological_support.application.dto.AttendanceDTO;

@Component
public class AtendanceInterectorFactory {

    @Autowired
    private RegisterAttendanceInterector registerAttendanceInterector;
    @Autowired
    private UpdateAttendanceInterector updateAttendanceInterector;


    public AttendanceInterector create(AttendanceDTO attendanceDTO) {
        if(attendanceDTO.id() == null || attendanceDTO.id().isBlank()) return registerAttendanceInterector;
        else return updateAttendanceInterector;
    }
    
}
