package br.com.jjohnys.psychological_care.patient.application.usecases.chenge_patient_status;

import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;

@Service
public class StopTreatment extends ChangePacienteStatusInterector {

    @Override
    public void validateChangeStatus(Patient patient, PatientStatusEnum newPatientStatus) {
        if(newPatientStatus == PatientStatusEnum.TREATMENT_STOPED &&
            !(patient.getStatus() == PatientStatusEnum.IN_TREATMENT)) {
                throw new PatientStatusException("Só é possivel interromper um tratamento quando o tratamento esta em andamento");
        }
    }

    

    
}
