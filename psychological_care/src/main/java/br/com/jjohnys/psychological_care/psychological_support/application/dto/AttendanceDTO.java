package br.com.jjohnys.psychological_care.psychological_support.application.dto;

import java.time.LocalDateTime;

public record AttendanceDTO(String id, String patientId, LocalDateTime dateSuport, String observation) {

        
}
