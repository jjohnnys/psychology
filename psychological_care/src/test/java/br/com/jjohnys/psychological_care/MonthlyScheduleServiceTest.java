package br.com.jjohnys.psychological_care;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.jjohnys.psychological_care.psychological_support.application.service.MonthlyScheduleService;

@SpringBootTest
public class MonthlyScheduleServiceTest {

    @Autowired
    private MonthlyScheduleService monthlyScheduleService;

    @Test
    public void showMonthlyScheduleOfPatienteWithAllWeek() {
        String[] days = monthlyScheduleService.getDays("paciente_2", 2023, 10);
        assertEquals("2023-11-07", days[0]);
        assertEquals("2023-11-21", days[1]);        
    }

    @Test
    public void showMonthlyScheduleOfPatienteWithPairWeek() {
        String[] days = monthlyScheduleService.getDays("paciente_5", 2023, 10);
        assertEquals("2023-11-03", days[0]);
        assertEquals("2023-11-10", days[1]);
        assertEquals("2023-11-17", days[2]);
        assertEquals("2023-11-24", days[3]);        
    }
    
}
