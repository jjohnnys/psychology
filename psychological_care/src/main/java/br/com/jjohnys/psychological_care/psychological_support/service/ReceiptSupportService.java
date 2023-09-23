package br.com.jjohnys.psychological_care.psychological_support.service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.exceptions.BusinessExceptions;
import br.com.jjohnys.psychological_care.patient.domain.Patient;
import br.com.jjohnys.psychological_care.patient.domain.Support;
import br.com.jjohnys.psychological_care.psychological_support.domain.ReceiptData;
import br.com.jjohnys.psychological_care.psychological_support.repository.SupportRepository;
import br.com.jjohnys.psychological_care.utils.DateUtils;

@Component
public class ReceiptSupportService {

    @Autowired
    private SupportRepository supportRepository;

    public ReceiptData getReceiptDataForPatienteByPeriod(Patient patient, LocalDate dateStart, LocalDate dateEnd) throws BusinessExceptions {
    
        List<Support> supports = supportRepository.getPeriodOfSupportByPatientAndPeriod(patient.getId(), dateStart, dateEnd);        
        if(supports.isEmpty()) throw new BusinessExceptions("Paciente sem atendimento cadastrado neste periodo");
        ReceiptData receiptData = ReceiptData.builder().
        patientName(patient.getName()).
        patientRG(patient.getRg()).
        patienteCPF(patient.getCpf()).
        totalSesions(supports.size()).
        totalValue(supports.size() * patient.getPlan().getPrice()).
        dates(supports.stream().map(suport -> DateUtils.localDateTimeToString(suport.getDateSuport())).collect(Collectors.joining(", "))).build();
        if(patient.getResponsible() != null) {
            receiptData.setResponsPatienteCPF(patient.getResponsible().getCpf());
            receiptData.setResponsPatienteRG(patient.getResponsible().getRg());
            receiptData.setResponsPatienteName(patient.getResponsible().getName());
        }

        return receiptData;
    }

}
