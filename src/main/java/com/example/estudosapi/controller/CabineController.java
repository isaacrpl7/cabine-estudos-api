package com.example.estudosapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.dtos.CabineDTO;
import com.example.estudosapi.model.dtos.CabineStatusDTO;
import com.example.estudosapi.model.dtos.ReservaDTO;
import com.example.estudosapi.service.CabineService;

@CrossOrigin
@RestController
@RequestMapping("cabines")
public class CabineController {
    
    @Autowired
    private CabineService service;

    @PostMapping
    public ResponseEntity<Cabine> cadastrar(@RequestBody(required = true) Cabine entity){
        return ResponseEntity.ok(service.cadastrar(entity));
    } 

    @GetMapping
    public ResponseEntity<List<Cabine>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CabineDTO> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findByIdDTO(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cabine> modificarStatus(@PathVariable("id") Long idCabine, @RequestBody CabineStatusDTO dto){
        return ResponseEntity.ok(service.modificarStatus(idCabine, dto));
    }

    @GetMapping("/{id}/proxima-reserva")
    public ResponseEntity<ReservaDTO> obterProximaReserva(@PathVariable("id") Long idCabine){
        return ResponseEntity.ok(service.obterProximaReserva(idCabine));
    }

    @GetMapping("/{id}/reservas")
    public ResponseEntity<List<ReservaDTO>> listarReservas(@PathVariable("id") Long idCabine){
        return ResponseEntity.ok(service.listarReservas(idCabine));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Cabine> limparReservas(@PathVariable Long id){
        return ResponseEntity.ok(service.limparReservas(id));
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cabine> cancelarReserva(@PathVariable Long id){
        return ResponseEntity.ok(service.cancelarReserva(id));
    }
}
