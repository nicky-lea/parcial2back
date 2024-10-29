package org.edu.usco.pw.exam_practice.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "clases")
public class Clase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, length = 20)
    private String name;

    @Column(name = "description", nullable = false, length = 20)
    private String description;


    @Column(name = "horaentrada", nullable = false)
    private String hent;

    @Column(name = "horasalida", nullable = false)
    private String hsal;

    @Column(name = "salon", length = 20)
    private String salon;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

}
