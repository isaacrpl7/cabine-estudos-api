package com.example.estudosapi.model.dtos;

import java.time.LocalDateTime;

public class ReservarCabineDTO {

    private String usuarioSenha;
    private String usuarioEmail;
    private LocalDateTime horario;

    
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
    public String getUsuarioSenha() {
        return usuarioSenha;
    }
    public void setUsuarioSenha(String usuarioSenha) {
        this.usuarioSenha = usuarioSenha;
    }

}
