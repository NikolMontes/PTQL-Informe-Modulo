package org.example.repository;

import org.example.entity.VoluntarioActividad;
import org.example.entity.VoluntarioActividadId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VoluntarioActividadRepository extends JpaRepository<VoluntarioActividad, VoluntarioActividadId> {
    List<VoluntarioActividad> findByActividad_Modulo_Idmodulo(Integer idmodulo);

    //List<VoluntarioActividad> findByActividadModuloId(Integer idmodulo);

}
