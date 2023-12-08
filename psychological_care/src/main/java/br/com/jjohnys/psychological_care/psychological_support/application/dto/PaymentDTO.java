package br.com.jjohnys.psychological_care.psychological_support.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record PaymentDTO(String id,String patientId, int year, int month, LocalDate date, BigDecimal value) {
    
}
