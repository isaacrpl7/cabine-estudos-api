package com.example.estudosapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.estudosapi.exceptions.NotFoundException;
import com.example.estudosapi.model.Usuario;
import com.example.estudosapi.repository.UsuarioRepository;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository repository;

    public Usuario save(Usuario entity){
        return repository.save(entity);
    }

    public List<Usuario> findAll(){
        return repository.findAll();
    }

    public Usuario findById(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("Usuario não encontrado com este id."));
    }

    public Usuario findByMatricula(String matricula){
        Optional<Usuario> usuario = repository.findByMatricula(matricula);
        return usuario.isPresent() ? usuario.get() : null;
    }

}
