package br.com.jjohnys.psychological_care.patient.application.usecases.change_patient_status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.exceptions.PatientStatusException;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.enums.PatientStatusEnum;
import br.com.jjohnys.psychological_care.patient.gateways.PatientScheduleRepository;

@Service
public class StopTreatment extends ChangePacienteStatusInterector {

    @Autowired
    private PatientScheduleRepository patientScheduleRepository;

    @Override
    public void validateChangeStatus(Patient patient, PatientStatusEnum newPatientStatus) {
        if(newPatientStatus == PatientStatusEnum.TREATMENT_STOPED &&
            !(patient.getStatus() == PatientStatusEnum.IN_TREATMENT)) {
                throw new PatientStatusException("Só é possivel interromper um tratamento quando o tratamento esta em andamento");
        }
    }

    @Override
    void change(Patient patient, PatientStatusEnum newPatientStatus) {
        patientScheduleRepository.deleteByPatientId(patient.getId());
        changeStatuPatient(patient.getId(), newPatientStatus);
    }

    

    
}
