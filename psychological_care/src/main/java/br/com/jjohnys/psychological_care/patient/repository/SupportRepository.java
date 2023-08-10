package br.com.jjohnys.psychological_care.patient.repository;

import br.com.jjohnys.psychological_care.patient.domain.Plan;

public interface SupportRepository {

    int insertSupport(Plan plan);
    int updateSupport(Plan plan);
    int deleteSupport(String id);
    Plan getSupportById(String id);
    Plan getPlanByDate(String type);

    
}
