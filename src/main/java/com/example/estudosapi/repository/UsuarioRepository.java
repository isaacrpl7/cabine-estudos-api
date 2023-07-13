package com.example.estudosapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.estudosapi.model.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    
    Optional<Usuario> findByMatricula(String matricula);
    Optional<Usuario> findByEmail(String email);
    boolean existsByMatricula(String matricula);
}
