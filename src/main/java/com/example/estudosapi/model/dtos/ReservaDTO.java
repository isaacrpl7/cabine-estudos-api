package com.example.estudosapi.model.dtos;

import java.time.LocalDateTime;

import com.example.estudosapi.model.Usuario;

public class ReservaDTO {

    private LocalDateTime horario;
    private Usuario usuario;
    

    public ReservaDTO() {
    }
    public ReservaDTO(LocalDateTime horario, Usuario usuario) {
        this.horario = horario;
        this.usuario = usuario;
    }
    public LocalDateTime getHorario() {
        return horario;
    }
    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }
    public Usuario getUsuario() {
        return usuario;
    }
    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    
}
