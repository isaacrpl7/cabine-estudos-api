package com.example.estudosapi.model.dtos;

import java.time.LocalDateTime;

public class ReservarCabineDTO {

    private String usuarioMatricula;
    private String usuarioEmail;
    private LocalDateTime horario;

    public String getUsuarioMatricula() {
        return usuarioMatricula;
    }
    public void setUsuarioMatricula(String usuarioMatricula) {
        this.usuarioMatricula = usuarioMatricula;
    }
    public String getUsuarioEmail() {
        return usuarioEmail;
    }
    public void setUsuarioEmail(String usuarioEmail) {
        this.usuarioEmail = usuarioEmail;
    }
    public LocalDateTime getHorario() {
        return horario;
    }
    public void setHorario(LocalDateTime horario) {
        this.horario = horario;
    }

}
