package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.stereotype.Service;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;

@Service
public class StopAtendenceInterector extends ChangePacienteStatusInterector {
    
    public StopAtendenceInterector() {
        patientStatusEnum = PatientStatusEnum.TREATMENT_STOPED;
    }
}
