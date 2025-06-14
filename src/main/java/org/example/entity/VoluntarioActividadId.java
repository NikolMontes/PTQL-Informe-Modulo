package org.example.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Objects;
@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class VoluntarioActividadId implements Serializable {

    @Column(name = "voluntario_idvoluntario")
    private Integer  idvoluntario;

    @Column(name = "actividad_idactividad")
    private Integer  idactividad;

}

