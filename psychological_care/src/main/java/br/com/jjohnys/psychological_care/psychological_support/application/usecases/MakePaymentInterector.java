package br.com.jjohnys.psychological_care.psychological_support.application.usecases;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.jjohnys.psychological_care.psychological_support.application.dto.PaymentDTO;
import br.com.jjohnys.psychological_care.psychological_support.domain.Patient;
import br.com.jjohnys.psychological_care.psychological_support.domain.Payment;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PatientRepository;
import br.com.jjohnys.psychological_care.psychological_support.gateways.PaymentRepository;

@Service
public class MakePaymentInterector {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private PatientRepository patientRepository;


    public void execute(PaymentDTO paymentDTO) {

        Patient patient = patientRepository.findPatientById(paymentDTO.patientId());
        Payment payment = new Payment(paymentDTO.id(), patient, paymentDTO.year(), paymentDTO.month(), paymentDTO.date(), paymentDTO.value());
        payment.validate();
        paymentRepository.insert(payment);
    }
    
}
