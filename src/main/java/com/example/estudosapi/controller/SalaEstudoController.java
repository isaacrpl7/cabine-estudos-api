package com.example.estudosapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.SalaEstudo;
import com.example.estudosapi.service.SalaEstudoService;

@RestController
@RequestMapping("sala-estudo")
public class SalaEstudoController {
    
    @Autowired
    private SalaEstudoService service;

    @PostMapping
    public ResponseEntity<SalaEstudo> cadastrar(@RequestBody(required = true) SalaEstudo entity){
        return ResponseEntity.ok(service.cadastrar(entity));
    } 

    @GetMapping
    public ResponseEntity<List<SalaEstudo>> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalaEstudo> findById(@PathVariable Long id){
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/cabines")
    public ResponseEntity<List<Cabine>> listarCabines(@PathVariable Long id){
        return ResponseEntity.ok(service.listarCabines(id));
    }

    @PutMapping("/{id}/cabines")
    public ResponseEntity<SalaEstudo> cadastrarCabine(@PathVariable Long id, 
        @RequestBody(required = true) Cabine entity){
        return ResponseEntity.ok(service.cadastrarCabine(id, entity));
    }
}
