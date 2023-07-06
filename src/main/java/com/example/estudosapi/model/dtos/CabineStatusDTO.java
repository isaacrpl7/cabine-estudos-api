package com.example.estudosapi.model.dtos;

import com.example.estudosapi.model.enums.EnumStatusCabine;

public class CabineStatusDTO {
    
    private EnumStatusCabine status;

    public EnumStatusCabine getStatus() {
        return status;
    }

    public void setStatus(EnumStatusCabine status) {
        this.status = status;
    }

}
