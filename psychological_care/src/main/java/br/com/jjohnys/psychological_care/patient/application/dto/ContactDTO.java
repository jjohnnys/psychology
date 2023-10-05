package br.com.jjohnys.psychological_care.patient.application.dto;

public record ContactDTO (
                String id, 
                String name, 
                String email, 
                String telephone, 
                String parentage, 
                String patientId, 
                String responsibleId) {
}
