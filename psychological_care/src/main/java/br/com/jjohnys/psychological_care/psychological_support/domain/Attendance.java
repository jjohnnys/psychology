package br.com.jjohnys.psychological_care.psychological_support.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Attendance {

    private String id;
    private Patient patient;
    private LocalDateTime dateSuport;
    private String observation;

}
