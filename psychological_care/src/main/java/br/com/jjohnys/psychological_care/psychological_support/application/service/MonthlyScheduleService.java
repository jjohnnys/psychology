package br.com.jjohnys.psychological_care.psychological_support.application.service;

import java.time.ZoneId;
import java.util.Calendar;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.domain.PatientSchedule;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientScheduleRepository;
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
        int j = 0;
        int orderWeek = 0;
        do {
            calendar.set(Calendar.DATE, i);
            int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
            if (dayOfWeek ==  patientSchedule.getDayOfWeek().getNumber()) {                
                if(skipWeek(orderWeek++, patientSchedule.getTypeWeek())) continue;               
                String day = ""; 
                if(j == daysOfMonth.length) break;
                day = DateUtils.localDateToString(calendar.getTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
                daysOfMonth[j++] = day;
            }
        } while(++i <= lastDayOfMonth);

        return daysOfMonth;
    }

    private boolean skipWeek(int orderWeek, PatientSchedule.TypeWeekEnum typeWeek) {
        if(typeWeek == PatientSchedule.TypeWeekEnum.ALL) return false;
        boolean skipWeek = true;
        boolean isPair = orderWeek % 2 == 0;
        if(typeWeek == PatientSchedule.TypeWeekEnum.PAIR && isPair) skipWeek = false;
        else if(typeWeek == PatientSchedule.TypeWeekEnum.ODD && !isPair) skipWeek = false;
        return skipWeek;
    }

    public static void main(String[] args) {
        //MonthlyScheduleService service = new MonthlyScheduleService();

        //var daysOfWeek = service.getDays(null, 2023, 10);

        //for (String dayOfWeek : daysOfWeek) {
          //  System.out.println("Dia da semana " + dayOfWeek);
       // }
        
    }

    
}
