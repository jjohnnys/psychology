package br.com.jjohnys.psychological_care.patient.repository;

import br.com.jjohnys.psychological_care.patient.domain.Plan;

public interface PlanRepository {
    
    int insertPlan(Plan plan);
    int updatePlan(Plan plan);
    int deletePlan(String id);
    Plan getPlanById(String id);
    Plan getPlanByType(String type);

}
