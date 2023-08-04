package br.com.jjohnys.psychological_care.patient.repository.jdbc;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import br.com.jjohnys.psychological_care.patient.domain.Plan;
import br.com.jjohnys.psychological_care.patient.repository.PlanRepository;

@Component
public class PlanJDBC implements PlanRepository{

    @Autowired
    JdbcTemplate jdbcTemplate;

    public int insertPlan(Plan plan) {
        return jdbcTemplate.update("insert into plan (id, type, price) values (?, ?, ?)",UUID.randomUUID().toString(), plan.getType(), plan.getPrice());        
    }

    public int updatePlan(Plan plan) {
        return jdbcTemplate.update("update plan set type, price) where id = ?", plan.getType(), plan.getType());
    }

    public int deletePlan(String id) {
        return jdbcTemplate.update("delete from plan where id = ?", id);
    }

    public Plan getPlanById(String id) {
        String query = "selecte * from plan where id = " + id;
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> 
            new Plan(rs.getString("id"), rs.getString("type"), rs.getInt("price")));
    }

    public Plan getPlanByType(String type) {
        String query = "selecte * from plan where type = " + type;
        return jdbcTemplate.queryForObject(query, (rs, rowNum) -> 
            new Plan(rs.getString("id"), rs.getString("type"), rs.getInt("price")));
    }    
}
