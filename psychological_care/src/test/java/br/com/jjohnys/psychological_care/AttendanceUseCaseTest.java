package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.AttendanceDTO;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.attendance_interector.AtendanceInterectorFactory;
import br.com.jjohnys.psychological_care.psychological_support.application.usecases.attendance_interector.AttendanceInterector;
import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;

@SpringBootTest
public class AttendanceUseCaseTest {

   @Autowired 
   private AtendanceInterectorFactory atendanceInterectorFactory;
   @Autowired
   private AttendanceRepository attendanceRepository; 
   
   @Test
   public void createAttendenceAndReturnAtendence() {

        var dateSuport = LocalDateTime.of(2017,Month.FEBRUARY,3,6,30);
        AttendanceDTO attendanceDTO = new AttendanceDTO(null, "paciente_1", dateSuport, "Foi de boa");
        AttendanceInterector attendanceInterector = atendanceInterectorFactory.create(attendanceDTO);
        attendanceInterector.execute(attendanceDTO);
        List<Attendance> attendance = attendanceRepository.getAttendanceByPatientId("paciente_1");
        assertEquals(attendance.stream().findFirst().get().getDateSuport(), dateSuport);
   }

   @Test
   public void createAttendenceAndReturnException() {

        var dateSuport = LocalDateTime.of(2023,Month.NOVEMBER,04,20,0);
        AttendanceDTO attendanceDTO = new AttendanceDTO(null, "paciente_1", dateSuport, "Foi de boa");
        AttendanceInterector attendanceInterector = atendanceInterectorFactory.create(attendanceDTO);        
        assertThrows(BusinessExceptions.class, () -> attendanceInterector.execute(attendanceDTO));
   }


    
}
