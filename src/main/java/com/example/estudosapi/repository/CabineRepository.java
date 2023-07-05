package com.example.estudosapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.estudosapi.model.Cabine;

@Repository
public interface CabineRepository extends JpaRepository<Cabine, Long>{
    
}
