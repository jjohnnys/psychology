package br.com.jjohnys.psychological_care.report.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.psychological_support.domain.Attendance;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.gateways.AttendanceRepository;
import br.com.jjohnys.psychological_care.report.domain.ReceiptData;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@Component
public class ReceiptSupportService {

    @Autowired
    private AttendanceRepository attendanceRepository;

    public ReceiptData getReceiptDataForPatienteByPeriod(Patient patient, LocalDate dateStart, LocalDate dateEnd) throws BusinessExceptions {
    
        List<Attendance> supports = attendanceRepository.getPeriodOfAttendanceByPatientAndPeriod(patient.getId(), dateStart, dateEnd);        
        if(supports.isEmpty()) throw new BusinessExceptions("Paciente sem atendimento cadastrado neste periodo");
        ReceiptData receiptData = ReceiptData.builder().
        patientName(patient.getName()).
        patientRG(patient.getRg()).
        patienteCPF(patient.getCpf().get()).
        totalSesions(supports.size()).
        dates(supports.stream().map(suport -> DateUtils.localDateTimeToString(suport.getDateSuport())).collect(Collectors.joining(", "))).build();
        

        return receiptData;
    }

}
