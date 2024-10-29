package org.edu.usco.pw.exam_practice.repository;

import org.edu.usco.pw.exam_practice.entities.UserEntity;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByRolesId(Long role_id);
    Optional<UserEntity> findByUsername(String username);

    boolean existsByUsername(String username);
    boolean existsById(Long id);
}
