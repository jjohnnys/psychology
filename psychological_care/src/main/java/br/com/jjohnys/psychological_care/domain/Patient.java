package br.com.jjohnys.psychological_care.domain;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@AllArgsConstructor
@ToString
public class Patient {

    @EqualsAndHashCode.Include
    private String id;
    private String name;
    private String cpf;
    private String rg;
    private LocalDate dateBirth;
    private String email;
    private Integer priceService;
    @Setter
    private String observation;
}
