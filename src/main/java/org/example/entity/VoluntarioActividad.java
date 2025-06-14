package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voluntario_has_actividad")
@Getter
@Setter
public class VoluntarioActividad {

    @EmbeddedId
    private VoluntarioActividadId id;

    @ManyToOne
    @MapsId("idvoluntario")
    @JoinColumn(name = "voluntario_idvoluntario")
    private Voluntario voluntario;

    @ManyToOne
    @MapsId("idactividad")
    @JoinColumn(name = "actividad_idactividad")
    private Actividad actividad;

    public VoluntarioActividad() {
        this.id = new VoluntarioActividadId();
    }

    public VoluntarioActividad(Voluntario voluntario, Actividad actividad) {
        this.voluntario = voluntario;
        this.actividad = actividad;
        this.id = new VoluntarioActividadId(
                voluntario.getIdvoluntario(), actividad.getIdactividad());
    }
}
