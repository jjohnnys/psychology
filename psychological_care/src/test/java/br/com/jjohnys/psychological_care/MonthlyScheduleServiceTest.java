package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.patient.application.service.MonthlyScheduleService;

@SpringBootTest
public class MonthlyScheduleServiceTest {

    @Autowired
    private MonthlyScheduleService monthlyScheduleService;

    @Test
    public void showMonthlySchedule() {
        String[] days = monthlyScheduleService.getDays("paciente_2", 2023, 10);
        assertEquals("2023-11-07", days[0]);
        assertEquals("2023-11-14", days[1]);
        assertEquals("2023-11-21", days[2]);
        assertEquals("2023-11-28", days[3]);        
    }
    
}
