package org.example.repository;

import org.example.entity.Voluntario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VoluntarioRepository extends JpaRepository<Voluntario, Integer> {
    Optional<Voluntario> findByDni(String dni);
}
