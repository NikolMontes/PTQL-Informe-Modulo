package org.example.repository;

import org.example.entity.Actividad;
import org.example.entity.Modulo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ActividadRepository extends JpaRepository<Actividad, Integer> {


    Optional<Actividad> findById(Integer idactividad);

    Optional<Actividad> findByNombreAndModulo(String nombre, Modulo modulo);
}
