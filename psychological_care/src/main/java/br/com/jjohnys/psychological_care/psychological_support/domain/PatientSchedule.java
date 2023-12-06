package br.com.jjohnys.psychological_care.psychological_support.domain;

import java.time.LocalTime;
import java.util.Arrays;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.DaysOfWeekEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PatientSchedule {    
    
    private Patient patient;
    private DaysOfWeekEnum dayOfWeek;
    private Integer timesOfMonth;
    private LocalTime time;
    private TypeWeekEnum typeWeek;

    public void vaidateTimesOfMonth() {
        if(this.timesOfMonth > 5) throw new BusinessExceptions("Não pode ter mais que cinco atendimentos por mês");
    }

    public enum TypeWeekEnum {

        PAIR("par"),
        ODD("impar"),
        ALL("todas");

        private String type;

        TypeWeekEnum(String type) {
            this.type = type;
        }

        public String getTypeWeek() {
            return type;
        }

        public static TypeWeekEnum getTypeWeekEnum(String type) {
            return Arrays.asList(TypeWeekEnum.values()).stream().
            filter(v -> v.getTypeWeek().equalsIgnoreCase(type)).findFirst().
            orElseThrow(() -> new BusinessExceptions("Tipo dasemana invalido"));     
        }
    }


}
