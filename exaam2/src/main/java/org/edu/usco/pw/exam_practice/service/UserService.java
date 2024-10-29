package org.edu.usco.pw.exam_practice.service;

import jakarta.persistence.EntityNotFoundException;
import org.edu.usco.pw.exam_practice.entities.Clase;
import org.edu.usco.pw.exam_practice.repository.ClaseRepository;
import org.edu.usco.pw.exam_practice.repository.RolRepository;
import org.edu.usco.pw.exam_practice.entities.Rol;
import org.edu.usco.pw.exam_practice.entities.UserEntity;
import org.edu.usco.pw.exam_practice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;


import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ClaseRepository claseRepository;

    public UserEntity registerUser(Long id, String name, String password, String username, List<String> roleNames) {
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setName(name);
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(username);

        List<Rol> userRoles = roleNames.stream()
                .map(roleName -> rolRepository.findByName(roleName)
                        .orElseThrow(() -> new RuntimeException("Rol no encontrado: " + roleName)))
                .collect(Collectors.toList());

        user.setRoles(userRoles);

        return userRepository.save(user);
    }

    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean userIdExists(Long id) {
        return userRepository.existsById(id);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserEntity user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe."));


        Collection<GrantedAuthority> authorities = mapRolesToAuthorities(user.getRoles());

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                authorities
        );
    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(Collection<Rol> roles) {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName()))
                .collect(Collectors.toList());
    }

    public List<String> obtenerNombresUsuariosPorRolDocente() {
        final Long DOCENTE_ROLE_ID = 3L; // ID del rol docente
        return userRepository.findByRolesId(DOCENTE_ROLE_ID)
                .stream()
                .map(UserEntity::getName)
                .collect(Collectors.toList());
    }

    public UserEntity findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));
    }








}