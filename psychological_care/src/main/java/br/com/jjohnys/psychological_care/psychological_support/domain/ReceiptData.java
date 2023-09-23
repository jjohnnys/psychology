package br.com.jjohnys.psychological_care.psychological_support.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReceiptData {

    private String responsPatienteName;
    private String responsPatienteCPF;
    private String responsPatienteRG;
    private String patientName;
    private String patienteCPF;
    private String patientRG;
    private Integer totalSesions;
    private Integer valueSession;
    private Integer totalValue;
    private String dates;
    private String time;


}
