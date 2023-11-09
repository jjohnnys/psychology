package br.com.jjohnys.psychological_care.patient.application.service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.patient.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.patient.domain.enums.DaysOfWeekEnum;
import br.com.jjohnys.psychological_care.patient.gateways.PatientScheduleRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MonthlyScheduleService {
    
    private PatientScheduleRepository patientScheduleRepository;

    public String[] getDays(String patientId, int year, int month) {

        String[] daysOfMonth = null;
        PatientSchedule patientSchedule = patientScheduleRepository.getScheduleByPatienteId(patientId);
        daysOfMonth = new String[patientSchedule.getTimesOfMonth()];
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        int i = 1;
        int j = -1;
        do {
            calendar.set(Calendar.DATE, i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            String day = "";
            if (dayOfWeek == DaysOfWeekEnum.TUESDAY.getDayOfWeek()) {                
             if(j == daysOfMonth.length) break;
             day = DateUtils.localDateToString(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
             daysOfMonth[++j] = day;
            }
        } while(++i <= lastDayOfMonth);

        return daysOfMonth;
    }

    public static void main(String[] args) {
        //MonthlyScheduleService service = new MonthlyScheduleService();

        //var daysOfWeek = service.getDays(null, 2023, 10);

        //for (String dayOfWeek : daysOfWeek) {
          //  System.out.println("Dia da semana " + dayOfWeek);
       // }
        
    }

    
}
