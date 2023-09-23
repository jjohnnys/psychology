package br.com.jjohnys.psychological_care.psychological_support.repository;

import java.time.LocalDate;
import java.util.List;

import br.com.jjohnys.psychological_care.psychological_support.Support;

public interface SupportRepository {

    int insertSupport(Support support);
    int updateSupport(Support support);
    int deleteSupport(String supportId);
    Support getSupportById(String supportId);
    public List<Support> getSupportByDate(LocalDate dateSuport);
    public List<Support> getSupportByPatient(String namePatiente);
    List<Support> getPeriodOfSupportByPatientAndPeriod(String patienteId, LocalDate dateStart, LocalDate dateEnd);

    
}
