package it.software.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Data
public class Agenda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "varchar(50)")
    private String titolo;
    @Column(columnDefinition = "text")
    private String descrizione;
    @Column(columnDefinition = "date")
    private LocalDate dataEvento;
    @Column(columnDefinition = "time")
    private LocalTime oraEvento;
    @Column(name = "dataregistrazione", columnDefinition = "timestamp")
    private LocalDateTime timeRegistrato;
}
