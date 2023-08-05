package br.com.jjohnys.psychological_care.patient.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Plan {

    @EqualsAndHashCode.Include
    private String id;
    private String type;
    private Integer price;
    
}
