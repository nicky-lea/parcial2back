package org.edu.usco.pw.exam_practice.repository;

import org.edu.usco.pw.exam_practice.entities.Rol;
import org.edu.usco.pw.exam_practice.entities.UserEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RolRepository extends JpaRepository<Rol, Long> {
    Optional<Rol> findByName(final String name);

}
