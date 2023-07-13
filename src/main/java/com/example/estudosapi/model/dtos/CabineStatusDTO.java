package com.example.estudosapi.model.dtos;

import java.time.LocalDateTime;

import com.example.estudosapi.model.enums.EnumStatusCabine;

public class CabineStatusDTO {
    
    private EnumStatusCabine status;
    private LocalDateTime horarioReserva;

    public EnumStatusCabine getStatus() {
        return status;
    }

    public void setStatus(EnumStatusCabine status) {
        this.status = status;
    }

    public LocalDateTime getHorarioReserva() {
        return horarioReserva;
    }

    public void setHorarioReserva(LocalDateTime horarioReserva) {
        this.horarioReserva = horarioReserva;
    }

}
