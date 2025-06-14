package org.example.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "voluntario")
@Getter
@Setter
public class Voluntario {
    @Id
    @Column(name = "idvoluntario")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idvoluntario;

    @Column(nullable = false)
    private String dni;

    @Column(name = "nombre_completo",nullable = false)
    private String nombreCompleto;

    @Column(nullable = true)
    private String email;

    @Column(nullable = true)
    private String celular;

    @Column(nullable = true)
    private Integer edad;

    @Column(nullable = true)
    private String distrito;


    @Column(name = "carrera_universitaria", nullable = true)
    private String carreraUniversitaria;
}

