package org.example.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "actividad")
@Getter
@Setter
public class Actividad {
    @Id
    @Column(name = "idactividad")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idactividad;
    private String nombre;

    @JoinColumn(name = "modulo_idmodulo")
    @ManyToOne
    private Modulo modulo;

}
