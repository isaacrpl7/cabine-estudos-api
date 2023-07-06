package com.example.estudosapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.estudosapi.exceptions.ConflictException;
import com.example.estudosapi.exceptions.NotFoundException;
import com.example.estudosapi.model.Cabine;
import com.example.estudosapi.model.SalaEstudo;
import com.example.estudosapi.repository.SalaEstudoRepository;

@Service
public class SalaEstudoService {
    
    @Autowired
    private SalaEstudoRepository repository;

    @Autowired
    private CabineService cabineService;

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<SalaEstudo> findAll(){
        return repository.findAll();
    }
    
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public SalaEstudo findById(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("SalaEstudo não encontrada com este id."));
    }

    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public List<Cabine> listarCabines(Long id){
        return repository.findById(id)
        .orElseThrow(() -> new NotFoundException("SalaEstudo não encontrada com este id."))
        .getCabines();
    }
    
    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo cadastrar(SalaEstudo entity){
        return repository.save(entity);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo cadastrarCabine(Long idSala, Cabine entity){
        SalaEstudo sala = findById(idSala);

        entity.setSalaEstudo(sala);
        sala.getCabines().add(entity);

        return repository.save(sala);
    }

    @Transactional(readOnly = false, propagation = Propagation.REQUIRED)
    public SalaEstudo adicionarCabine(Long idSala, Long idCabine){
        SalaEstudo sala = findById(idSala);
        Cabine cabine = cabineService.findById(idCabine);

        if(cabine.getSalaEstudo() != null)
            throw new ConflictException("A cabine informada já possui sala");

        cabine.setSalaEstudo(sala);
        sala.getCabines().add(cabine);
        return repository.save(sala);
    }
}
