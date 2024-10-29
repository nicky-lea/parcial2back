package org.edu.usco.pw.exam_practice.service;

import org.edu.usco.pw.exam_practice.entities.Clase;
import org.edu.usco.pw.exam_practice.excepciones.ResourceNotFoundException;
import org.edu.usco.pw.exam_practice.repository.ClaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClaseService {

    @Autowired
    private ClaseRepository claseRepository;

    public List<Clase> getAllClases() {
        return claseRepository.findAll();
    }

    public Clase getClaseById(Long id) {
        return claseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Clase no encontrada: " + id));
    }

    public Clase crearClase(String name, String hent, String hsal, String salon, String description) {
        Clase nuevaClase = new Clase();

        nuevaClase.setName(name);
        nuevaClase.setHent(hent);
        nuevaClase.setHsal(hsal);
        nuevaClase.setDescription(description);
        nuevaClase.setSalon(salon);


        return claseRepository.save(nuevaClase);
    }

    public Clase updateClase(Long id, Clase clase) {
        Clase existingClase = getClaseById(id);
        existingClase.setName(clase.getName());
        existingClase.setDescription(clase.getDescription());
        existingClase.setHent(clase.getHent());
        existingClase.setHsal(clase.getHsal());
        existingClase.setSalon(clase.getSalon());
        return claseRepository.save(existingClase);
    }


    public void eliminarClase(Long id) {
        claseRepository.deleteById(id);
    }
    public Clase guardarClase(Clase nuevaClase) {
        return claseRepository.save(nuevaClase);
    }



}
