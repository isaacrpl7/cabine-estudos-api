package com.example.estudosapi.model;

import com.example.estudosapi.model.enums.EnumStatusCabine;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Cabine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column
    private EnumStatusCabine status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "sala_id")
    private SalaEstudo salaEstudo;

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

    
}
