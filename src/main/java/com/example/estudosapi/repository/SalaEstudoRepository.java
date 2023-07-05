package com.example.estudosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.estudosapi.model.SalaEstudo;

public interface SalaEstudoRepository extends JpaRepository<SalaEstudo, Long>{
    
}
