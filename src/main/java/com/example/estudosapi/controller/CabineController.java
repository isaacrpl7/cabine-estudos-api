package com.example.estudosapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.enums.EnumStatusCabine;
import com.example.estudosapi.service.CabineService;

@RestController
@RequestMapping("/cabines")
public class CabineController {
    
    @Autowired
    private CabineService service;

    @GetMapping
    public ResponseEntity<List<Cabine>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cabine> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Cabine> modificarStatus(@PathVariable Long idCabine, EnumStatusCabine novoStatus){
        return ResponseEntity.ok(service.modificarStatus(idCabine, novoStatus));
    }

}
