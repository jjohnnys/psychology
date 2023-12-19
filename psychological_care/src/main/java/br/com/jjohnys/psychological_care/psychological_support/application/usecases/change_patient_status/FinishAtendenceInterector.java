package br.com.jjohnys.psychological_care.psychological_support.application.usecases.change_patient_status;

import org.springframework.stereotype.Service;
import br.com.jjohnys.psychological_care.psychological_support.domain.enums.PatientStatusEnum;

@Service
public class FinishAtendenceInterector extends ChangePacienteStatusInterector {
    
    public FinishAtendenceInterector() {
        patientStatusEnum = PatientStatusEnum.TREATMENT_FINISHED;
    }
}
