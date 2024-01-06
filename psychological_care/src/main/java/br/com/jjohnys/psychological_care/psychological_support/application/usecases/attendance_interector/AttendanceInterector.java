package br.com.jjohnys.psychological_care.psychological_support.application.usecases.attendance_interector;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.application.dto.AttendanceDTO;
import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;


public abstract class AttendanceInterector {

    @Autowired
    private PatientRepository patientRepository;
    @Autowired
    protected AttendanceRepository attendanceRepository;

    public abstract void save(Attendance attendance);

    public void execute(AttendanceDTO attendanceDTO) {
        Attendance attendance = validate(attendanceDTO);
        save(attendance);
    }
    
    public Attendance validate(AttendanceDTO attendanceDTO) {
        Patient patient = patientRepository.findPatientById(attendanceDTO.patientId());
        if(patient == null) throw new BusinessExceptions("Paciente nao cadastrado");
        Attendance attendance = attendanceRepository.getAttendanceByDateTime(attendanceDTO.dateSuport());
        if(attendance != null) throw new BusinessExceptions("Já existe atendimento nesta data e hora");

        return attendance = new Attendance(null, patient, attendanceDTO.dateSuport(), attendanceDTO.observation());        
    }


    
}
