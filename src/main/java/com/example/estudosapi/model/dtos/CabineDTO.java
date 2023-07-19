package com.example.estudosapi.model.dtos;

import java.time.LocalDateTime;

import com.example.estudosapi.model.enums.EnumStatusCabine;

public class CabineDTO {
    
    private Long id;
    private EnumStatusCabine status;
    private LocalDateTime horarioInicial;
    private LocalDateTime horarioFinal;

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
    public LocalDateTime getHorarioInicial() {
        return horarioInicial;
    }
    public void setHorarioInicial(LocalDateTime horarioInicial) {
        this.horarioInicial = horarioInicial;
    }
    public LocalDateTime getHorarioFinal() {
        return horarioFinal;
    }
    public void setHorarioFinal(LocalDateTime horarioFinal) {
        this.horarioFinal = horarioFinal;
    }
    
}
