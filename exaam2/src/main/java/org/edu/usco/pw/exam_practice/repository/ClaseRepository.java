package org.edu.usco.pw.exam_practice.repository;

import org.edu.usco.pw.exam_practice.entities.Clase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ClaseRepository extends JpaRepository<Clase, Long> {
}