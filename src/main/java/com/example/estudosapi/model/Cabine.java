package com.example.estudosapi.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.example.estudosapi.model.enums.EnumStatusCabine;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Cabine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(nullable = false)
    private EnumStatusCabine status;

    private LocalDateTime horarioReserva;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id")
    private SalaEstudo salaEstudo;

    @JsonIgnore
    @OneToMany(mappedBy = "cabine", cascade = CascadeType.ALL)
    private List<Reserva> reservas = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EnumStatusCabine getStatus() {
        return status;
    }

    public void setStatus(EnumStatusCabine status) {
        this.status = status;
    }

    public SalaEstudo getSalaEstudo() {
        return salaEstudo;
    }

    public void setSalaEstudo(SalaEstudo salaEstudo) {
        this.salaEstudo = salaEstudo;
    }

    public LocalDateTime getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(LocalDateTime horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

    public List<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(List<Reserva> reservas) {
        this.reservas = reservas;
    }

    
}
